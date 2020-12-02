import java.util.*;

public class ZhuangChe
{
	//每日名字
	private String[] names = {"曹", "郭", "保", "卫", "小娃", "保军", "小宁", "杜", "亮", "常军", "二朋", "毛旦", "李", "老马"};
	//每一吨的金额
	public static float MONEYPERTON = 6.5f;
	//每日吨位
	private ArrayList<Integer> todayWeight = new ArrayList<>();
	//每个人的对象
	private Worker1[] workers = new Worker1[names.length];

	//初始化所有对象
	public void init()
	{
		for (int i = 0; i < workers.length; i++)
		{
			workers[i] = new Worker1(names[i]);
		}
	}

	//利用输入的string数据来添加数据
	public boolean addData(String input)
	{
		String[] current = input.split(" ");

		//定义吨位
		int first;
		try
		{
			first = Integer.parseInt(current[0]);
		}
		catch (Exception e)
		{
			System.out.println("数据转换错误");
			return false;
		}
		
		//保存每一个的索引值
		int[] temIndex = new int[current.length - 1];
		try
		{
			for (int i = 0; i < (current.length - 1); i++)
			{
				int thisIndex = search(current[i + 1]);
				if (thisIndex == -1)
				{
					throw new Exception();
				}
				temIndex[i] = thisIndex;
			}
		}
		catch (Exception e)
		{
			System.out.println("人名搜索错误");
			return false;
		}
		//添加吨位
		todayWeight.add(first);
		//给每个人添加数据
		for (int i = 1; i < current.length; i++)
		{
			workers[temIndex[i - 1]].addItem(first, (current.length - 1));
		}
		return true;
	}

	//返回今天总吨位
	public int totalWeight()
	{
		int sum = 0;
		for (int i = 0; i < todayWeight.size(); i++)
		{
			sum = sum + todayWeight.get(i);
		}
		return sum;
	}
	
	//计算索引值
	public int search(String name)
	{
		for (int i = 0; i < names.length; i++)
		{
			if (name.equals(names[i]))
			{
				return i;
			}
		}
		return -1;
	}

	//输出当日数据和吨位
	public void printlnData()
	{
		for (var ele : workers)
		{
			System.out.println(ele);
		}
		System.out.println("总吨位为：" + totalWeight());
		float totalMoney = (float)(totalWeight() * MONEYPERTON);
		float totalOfWokers = 0.0f;
		float diff = 0.0f;
		for (var ele : workers)
		{
			totalOfWokers = totalOfWokers + ele.getMoney();
		}
		totalOfWokers = (float) (Math.round(totalOfWokers * 10.0f) / 10.0);
		System.out.println("今天" + totalWeight() + "吨的总钱为：" + totalMoney);
		System.out.println("今天工人能领到的钱总钱为：" + totalOfWokers);
		diff = (float)Math.round((totalMoney - totalOfWokers) * 10.0f) / 10.0f;
		System.out.println("今天钱的差值为：" + diff);
		System.out.println("----------------复制专用数据：-------------------------------------");
		for (var ele : workers)
		{
			System.out.println(ele.getMoney());
		}

	}

	public static void main(String[] args) 
	{
		ZhuangChe zhuangChe = new ZhuangChe();
		//初始化对象数组
		zhuangChe.init();
		System.out.println("这是计算装车的-------单价" + MONEYPERTON);
		System.out.println("请以'吨位 名字 名字'的形式输入数据，输入exit表示输入结束");
		Scanner sc = new Scanner(System.in);
		while (true)
		{
			String inputLine = sc.nextLine();
			//如果输入exit，则代表今天数据输入完毕 则退出
			if (inputLine.equals("exit"))
			{
				zhuangChe.printlnData();
				sc.close();
				break;
			}
			//如果不是，则将当前数据添加到数据库中
			//如果输入成功，则继续
			if (zhuangChe.addData(inputLine))
			{
				System.out.println("输入成功，请继续，输入exit表示输入结束");
				continue;
			}
			else 
			{
				System.out.println("输入失败，请检查名字和格式'吨位 名字 名字'，输入exit表示输入结束");
				continue;
			}
		}
	}
}

class Worker1
{
	private String name;//工人的名字
	private ArrayList<Integer> weightItem = new ArrayList<>();//代表吨位，
	private ArrayList<Integer> numberItem = new ArrayList<>();//第二个代表几个人装
	private float money = 0.0f;
	private boolean flag = false;

	//new对象时一定要有名字
	public Worker1(String name)
	{
		this.name = name;
	}

	//给对象添加数据
	public void addItem(int weight, int numOfPeople)
	{
		weightItem.add(weight);
		numberItem.add(numOfPeople);
	}
	
	private void updateMoney()
	{
		for (int i = 0; i < weightItem.size(); i++)
		{
 			money += Math.floor((weightItem.get(i) * ZhuangChe.MONEYPERTON / (numberItem.get(i) * 1.0)) * 10.0) / 10.0; //保留一位小数
		}
		flag = true;
	}

	public float getMoney()
	{	
		if (! flag)
		{
			updateMoney();
		}
		return money;
	}

	//toString 方法
	public String toString()
	{
		return name + "\t" + getMoney();
	}
}