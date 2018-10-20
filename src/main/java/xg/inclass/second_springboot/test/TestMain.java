package xg.inclass.second_springboot.test;

import lombok.Getter;
import lombok.Setter;
import org.junit.Test;
import org.springframework.beans.factory.BeanInitializationException;
import xg.inclass.second_springboot.SecondSpringbootApplication;
import xg.inclass.second_springboot.dto.Student;
import xg.inclass.second_springboot.dto.dto.BookRequsetParam;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.*;
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
            //result = ArrayUtils.addAll(result, ints);
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

    private void autoReplace(String filePath,String outPath) throws IOException{
        File file=new File(filePath);
        Long fileLength=file.length();
        byte[] fileContext=new byte[fileLength.intValue()];
        FileInputStream in=new FileInputStream(filePath);
        in.read(fileContext);
        in.close();
        List<String > tables = Arrays.asList(
                "gt_goods",
                "ct_category",
                "st_district",
                "st_jdaddress");
        PrintWriter out = null;
        for (String table : tables) {
            String str=new String(fileContext,"GB2312");
            str=str.replace("seq_gt_business","seq_"+table);
            String  fileName=outPath+table+".sql";
            out=new PrintWriter(fileName);
            System.out.println("create_seq_"+table+".sql");
            //out.write(str.toCharArray());
            out.write(str);
            out.flush();
        }

        out.close();
        System.out.println("完成");
    }
    public static void main(String[] args) throws Exception {
//        new TestMain().autoReplace("F:\\create_seq_gt_business.sql","F:\\create_seq_");
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        BookRequsetParam bean = new BookRequsetParam();
        StringBuilder sb = new StringBuilder("Bean state is invalid: ");
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        if (!result.isEmpty()) {
            for (Iterator<ConstraintViolation<Object>> it = result.iterator(); it.hasNext();) {
                ConstraintViolation<Object> violation = it.next();
                sb.append(violation.getPropertyPath()).append(" - ").append(violation.getMessage());
                if (it.hasNext()) {
                    sb.append("; ");
                }
            }
        }
        System.out.println(sb.toString());
        /*
        Properties prop = new Properties();
        InputStream in = TestMain.class.getClass().getResourceAsStream("/config/error_code.properties");
        try {
            prop.load(in);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("1001="+prop.getProperty("1001"));*/

        int[] source = new int[]{3, 0, 1, 2, 5, 3, 0, 0};
        //new TestMain().sort2(source);
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
