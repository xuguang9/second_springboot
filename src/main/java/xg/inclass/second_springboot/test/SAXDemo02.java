package xg.inclass.second_springboot.test;

import lombok.Data;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * @Description:
 * @Author: xuguang
 * @CreateDate: 2018/5/20 21:35
 * @Version: 1.0
 */
public class SAXDemo02 {
    /**
     * 使用Sax方法解析students.xml文件，保存到集合中
     * @param args
     */
    public static void main(String[] args) throws Exception{
        //1.创建工厂   SAX分析工厂
        SAXParserFactory factory = SAXParserFactory.newInstance();

        //2.创建解释器
        SAXParser parser = factory.newSAXParser();

        //3.从解释器获取读取器
        XMLReader reader = parser.getXMLReader();
        StudentContentHandler2 studentContentHandler2 = new StudentContentHandler2();
        //4.设置内容处理器
        reader.setContentHandler(studentContentHandler2);

        //5.解析
        InputStream stream = SAXDemo02.class.getResourceAsStream("/file/student.xml");
        reader.parse(new InputSource(stream));

        for(Student s:studentContentHandler2.getList()){
            System.out.println(s.getId()+"--"+s.getName()+"--"+s.getAge()+"--"+s.getSex());
        }
    }
}

@Data
class StudentContentHandler2 extends DefaultHandler {
    ArrayList<Student> list =new ArrayList<Student>();
    String currentTagName;
    Student student  = null;


    //内容处理器开始标签
    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {

        currentTagName = qName;
        if(qName.equals("student")){
            student = new Student();

        }
    }
    //内容处理器 结束标签
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        currentTagName = "";
        if(qName.equals("student")){
            list.add(student);
        }

    }
    //内容处理 处理内容标签
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {

        if(currentTagName.equals("name")){
            student.setName(new String(ch,start,length));
        }else if(currentTagName.equals("age")){
            student.setAge(Integer.parseInt(new String(ch,start,length)));
        }else if(currentTagName.equals("sex")){
            student.setSex(new String(ch,start,length));
        }else if(currentTagName.equals("id")){
            student.setId(Integer.parseInt(new String(ch,start,length)));
        }

    }


}

@Data
class Student{
    private int id;
    private String name;
    private int age;
    private String sex;
    public Student() {
        super();
    }
    public Student(int id, String name, int age, String sex) {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }


}