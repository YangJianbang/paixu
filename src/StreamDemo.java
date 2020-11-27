import java.util.ArrayList;
import java.util.List;

public class StreamDemo {
    public static void main(String[] args) {
        User u1 = new User(1,"aa",20);
        User u2 = new User(2,"bb",10);
        User u3 = new User(3,"cc",40);
        User u4 = new User(4,"dd",30);
        List<User> list = new ArrayList<>();
        list.add(u1);
        list.add(u2);
        list.add(u3);
        list.add(u4);

        list.stream().
                filter((p)->{ return p.getId()%2==0; }).
                filter((p)->{ return p.getAge()>20; }).
                map((p)->{ return p.getName().toUpperCase(); }).sorted((o1, o2) -> {
            return o2.compareTo(o1);
        }).limit(1).
                forEach(System.out::println);

    }
}
