package hashmap;

public class Main {

    public static void main(String[] args) {

        MyHashMap<String, Integer> map = new MyHashMap<>();

        map.put("A", 1);
        map.put("B", 2);

        System.out.println(map.get("A"));
        System.out.println(map.get("B"));
        System.out.println(map.put("A", 100));
        System.out.println(map.get("A"));
        map.remove("B");
        System.out.println(map.get("B"));
        System.out.println(map.size());
        System.out.println(map.isEmpty());

        System.out.println("Проверка на коллизии");

        MyHashMap<TestKey, Integer> map2 = new MyHashMap<>();

        TestKey k1 = new TestKey("A");
        TestKey k2 = new TestKey("B");
        TestKey k3 = new TestKey("C");

        map2.put(k1, 1);
        map2.put(k2, 2);
        map2.put(k3, 3);

        System.out.println(map2.get(k1));
        System.out.println(map2.get(k2));
        System.out.println(map2.get(k3));

        map2.remove(k2);

        System.out.println(map2.get(k1));
        System.out.println(map2.get(k2));
        System.out.println(map2.get(k3));

    }

}
