package osaki.mylang.controller;

import osaki.mylang.model.PrgState;
import osaki.mylang.model.values.IValue;
import osaki.mylang.model.values.RefValue;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GarbageCollector {
    public static Map<Integer, IValue> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer,IValue> heap) {
        return heap.entrySet().stream()
                .filter(e-> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static Map<Integer, IValue> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer,IValue> heap, List<Integer> heap_addresses) {
        return heap.entrySet().stream()
                .filter(e-> { return symTableAddr.contains(e.getKey()) || heap_addresses.contains(e.getKey()); })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static List<Integer> getAddrList(Collection<IValue> values) {
        return values.stream()
                .filter(v->v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public static void conservativeGarbageCollector(List<PrgState> prgStates) {
        List<Integer> symTableAddr = Objects.requireNonNull(prgStates.stream()
                .map(prgState -> getAddrList(prgState.getSymTable().getContent().values()))
                .map(Collection::stream)
                .reduce(Stream::concat).orElse(null)).toList();

        // prg.getHeap().getContent(),
        List<Integer> heapAddr = getAddrList(prgStates.get(0).getHeap().getContent().values());
        Map<Integer, IValue> newHeap = safeGarbageCollector(symTableAddr, prgStates.get(0).getHeap().getContent(), heapAddr);

        prgStates.forEach(prgState -> prgState.getHeap().setContent(newHeap));
    }
}
