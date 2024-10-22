import newpson.util.Container;
import java.util.ArrayList;

public class Main
{
	public static void main(String args[])
	{
		Container container = new Container();
		container.add("Hello");
		container.add("world");
		container.add("this");

		ArrayList<String> arrayList = new ArrayList<>();
		arrayList.add("is");
		arrayList.add("a");
		arrayList.add("test");
		arrayList.add("!");

		container.addAll(arrayList);
		System.out.println(container);

		int lastAdded = container.addedIndex();
		System.out.println("Last added element index: " + lastAdded);

		container.remove(lastAdded);
		container.remove("Hello");
		System.out.println(container);

		int i = container.find("is");
		if (i >= 0)
			System.out.println("Object behind " + i + ": \"" + container.at(i) + "\"");
	}
}
