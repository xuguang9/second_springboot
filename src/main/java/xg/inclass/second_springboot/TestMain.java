package xg.inclass.second_springboot;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TestMain {

    @Setter
    @Getter
    class SortBean {
        int flag = 0;
        int num = 0;
        int[] ints;

        public SortBean(int flag, int num) {
            this.flag = flag;
            this.num = num;
        }

        public int[] getInts() {
            ints = new int[num];
            for (int i = 0; i < ints.length; i++) {
                ints[i] = flag;
            }
            return ints;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SortBean sortBean = (SortBean) o;
            return flag == sortBean.flag;
        }

        @Override
        public int hashCode() {
            return Objects.hash(flag, num);
        }
    }

    private int[] sort(int[] source) {
        Map<Integer, SortBean> map = new HashMap<>();
        for (int i : source) {
            if (map.containsKey(i)) {
                SortBean sortBean = map.get(i);
                sortBean.setNum(sortBean.getNum() + 1);
            } else {
                map.put(i, new SortBean(i, 1));
            }
        }
        List<Map.Entry<Integer, SortBean>> list = new ArrayList<Map.Entry<Integer, SortBean>>(map.entrySet());
        list = list.stream().sorted((a, b) -> a.getValue().getNum() - b.getValue().getNum()).collect(Collectors.toList());

        int[] result = new int[]{};
        for (Map.Entry<Integer, SortBean> entry : list) {
            int[] ints = entry.getValue().getInts();
            result = ArrayUtils.addAll(result, ints);
        }
        return result;
    }

    private int[] sort2(int[] source) {
        Map<Integer, List> map = new HashMap<>();
        for (int i : source) {
            List list = null;
            if (map.containsKey(i)) {
                list = map.get(i);
                list.add(i);
            } else {
                list = new ArrayList();
                list.add(i);
                map.put(i, list);
            }
        }
        List<Map.Entry<Integer, List>> list = new ArrayList<Map.Entry<Integer, List>>(map.entrySet());
        list = list.stream().sorted((a, b) -> a.getValue().size() - b.getValue().size()).collect(Collectors.toList());
        List li = new ArrayList();
        list.stream().map(entry->entry.getValue()).reduce((lii,b)->{
            lii.addAll(b) ;
            return lii;
        }).get();
        System.out.println(list.get(0));
        int[] result = new int[]{};
        return result;
    }


    public static void main(String[] args) {
        int[] source = new int[]{3, 0, 1, 2, 5, 3, 0, 0};
        new TestMain().sort2(source);
        //  source =new TestMain().sort(source);
        // System.out.println(Arrays.toString(source));
       /* List<Integer> list = new ArrayList<>();  //构造list，填充值
        //list.add(2,3);
        list.add(3);
        list.add(2);*/
        //list = list.stream().sorted((n1, n2) -> n1.compareTo(n2)).collect(Collectors.toList());
        //System.out.println(list);

        //System.out.println(list);
        //listStream();
        // Map<Integer,Integer> map = new TreeMap<>((a,b)->Integer.compare(a,b));
        Map<Integer, Integer> map = new HashMap<>();
        map.put(4, 3);
        map.put(2, 1);
        map.put(1, 4);
        //System.out.println(map.toString());

    /*    int[] a = new int[]{1,0,0};
        int[] sort = sort(a);
        System.out.println(Arrays.toString(sort));*/
    }


    private static void listStream() {
        List<Student> list = new ArrayList<Student>();
        list.add(new Student(1, "Mahesh", 12));
        list.add(new Student(2, "Suresh", 15));
        list.add(new Student(3, "Nilesh", 10));

        System.out.println("---Natural Sorting by Name---");
        List<Student> slist = list.stream().sorted().collect(Collectors.toList());
        slist.forEach(e -> System.out.println("Id:" + e.getId() + ", Name: " + e.getName() + ", Age:" + e.getAge()));

        System.out.println("---Natural Sorting by Name in reverse order---");
        slist = list.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        slist.forEach(e -> System.out.println("Id:" + e.getId() + ", Name: " + e.getName() + ", Age:" + e.getAge()));

        System.out.println("---Sorting using Comparator by Age---");
        slist = list.stream().sorted(Comparator.comparing(
                //Student::getAge
                new Function<Student, Integer>() {
                    @Override
                    public Integer apply(Student student) {
                        return student.getAge();
                    }
                }
        )).collect(Collectors.toList());
        slist.forEach(e -> System.out.println("Id:" + e.getId() + ", Name: " + e.getName() + ", Age:" + e.getAge()));

        System.out.println("---Sorting using Comparator by Age with reverse order---");
        slist = list.stream().sorted(Comparator.comparing(Student::getAge).reversed()).collect(Collectors.toList());
        slist.forEach(e -> System.out.println("Id:" + e.getId() + ", Name: " + e.getName() + ", Age:" + e.getAge()));
    }
}
