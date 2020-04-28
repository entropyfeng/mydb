package base;

import org.junit.Test;

import java.io.*;

public class TestProtoBuf {



    @Test
    public void testStudent()throws IOException{
        Student student=new Student("王");
        FileOutputStream fileOutputStream=new FileOutputStream("./student");
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeObject(student);
        objectOutputStream.close();
    }

    @Test
    public void testTeacher()throws IOException{
        Teacher teacher=new Teacher("王");
        FileOutputStream fileOutputStream=new FileOutputStream("./teacher");
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeObject(teacher);
        objectOutputStream.close();
    }

    public static class  Student implements Externalizable {

        public Student(String name){
            this.name=name;
            man=false;
        }

        public Student(){

        }
        private String name;
        private Boolean man;
        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeBytes(name);
            out.writeBoolean(man);
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

        }
    }
    public static class  Teacher implements Serializable  {

        public Teacher(String name){
            this.name=name;
            man=false;
        }

        private String name;
        private Boolean man;

    }
}
