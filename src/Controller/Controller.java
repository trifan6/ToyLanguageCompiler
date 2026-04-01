package Controller;

import Exceptions.EmptyStackException;
import Exceptions.MyException;
import Model.ADT.MyIStack;
import Model.PrgState;
import Model.Statements.IStmt;
import Model.Values.RefValue;
import Model.Values.Value;
import Repository.IRepository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


public class Controller
{
    private final IRepository repo;
    private boolean displayFlag = true;
    private ExecutorService executor;

    public Controller(IRepository repo)
    {
        this.repo = repo;
        this.executor = Executors.newFixedThreadPool(2);
    }

    public void setDisplayFlag(boolean v)
    {
        this.displayFlag = v;
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList)
    {
        return inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    public void oneStepForAll(List<PrgState> prgList) throws InterruptedException
    {
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        });

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) (() -> {
                    return p.oneStep();
                }))
                .collect(Collectors.toList());

        List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .filter(p -> p != null)
                .collect(Collectors.toList());

        prgList.addAll(newPrgList);

        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        });

        repo.setPrgList(prgList);
    }

    public void allStep() throws MyException
    {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
        while(!prgList.isEmpty())
        {
            prgList.get(0).getHeap().setContent(
                    safeGarbageCollector(
                            getAddressFromSymTable(
                                    prgList.stream()
                                            .flatMap(p->p.getSymTable().getAll().values().stream())
                                            .collect(Collectors.toList())
                            ),
                            prgList.get(0).getHeap().getContent()
                            )
                    );

            try
            {
                oneStepForAll(prgList);
            } catch (InterruptedException e) {
                throw new MyException(e.getMessage());
            }

            prgList = removeCompletedPrg(repo.getPrgList());
        }

        executor.shutdownNow();

        repo.setPrgList(prgList);
    }

    private List<Integer> getAddressFromSymTable(Collection<Value> symTableValues)
    {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).getAddress())
                .collect(Collectors.toList());
    }

    private Set<Integer> getReachableAddresses(List<Integer> symTableAddresses, Map<Integer, Value> heap)
    {
        Set<Integer> reachable = new HashSet<>();
        Deque<Integer> stack = new ArrayDeque<>();

        for (Integer a : symTableAddresses) {
            if (a != null && a != 0 && heap.containsKey(a))
            {
                reachable.add(a);
                stack.push(a);
            }
        }

        while (!stack.isEmpty())
        {
            Integer a = stack.pop();
            Value val = heap.get(a);
            if (val instanceof RefValue)
            {
                int nestedAddr = ((RefValue) val).getAddress();
                if (nestedAddr != 0 && heap.containsKey(nestedAddr) && !reachable.contains(nestedAddr))
                {
                    reachable.add(nestedAddr);
                    stack.push(nestedAddr);
                }
            }
        }
        return reachable;
    }

    private Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddresses, Map<Integer, Value> heap)
    {
        Set<Integer> reachable = getReachableAddresses(symTableAddresses, heap);

        return heap.entrySet().stream()
                .filter(e -> reachable.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public IRepository getRepo()
    {
        return repo;
    }

}