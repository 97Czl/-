import java.util.*;

public class ZaGong
{
	//每日名字
	private String[] names = {"曹", "郭", "保", "卫", "小娃", "保军", "小宁", "杜", "亮", "常军", "二朋", "毛旦", "李"};
	//每份工的单价
	public static float MONEYPER = 50;
	//总资金
	private ArrayList<Integer> totalSum = new ArrayList<>();
	//每个人的对象
	private Worker2[] workers = new Worker2[names.length];

	//初始化所有对象
	public void init()
	{
		for (int i = 0; i < workers.length; i++)
		{
			workers[i] = new Worker2(names[i]);
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

		//添加吨位
		totalSum.add(first);
		
		//保存每一个的索引值
		int[] temIndex = new int[current.length - 1];
		try
		{
			for (int i = 0; i < (temIndex.length); i++)
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
		//给每个人添加数据
		for (int i = 0; i < temIndex.length; i++)
		{
			workers[temIndex[i]].addItem(first, (current.length - 1));
		}
		return true;
	}

	//返回总资金
	public int getTotalSum()
	{
		int sum = 0;
		for (int i = 0; i < totalSum.size(); i++)
		{
			sum = sum + totalSum.get(i);
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

	//返回所有工人工资
	private float getSumOfWage()
	{
		float sum = 0.0f;
		for (int i = 0; i < workers.length; i++)
		{
			sum = sum + workers[i].getMoney();
		}
		return sum;
	}

	//输出当日数据和吨位
	public void printlnData()
	{
		for (var ele : workers)
		{
			System.out.println(ele);
		}
	}

	public static void main(String[] args) 
	{
		ZaGong zaGong = new ZaGong();
		//初始化对象数组
		zaGong.init();
		System.out.println("这是计算杂工的-------单价" + MONEYPER);
		System.out.println("请以'批次 名字 名字'的形式输入数据，输入exit表示输入结束");
		Scanner sc = new Scanner(System.in);
		while (true)
		{
			String inputLine = sc.nextLine();
			//如果输入exit，则代表今天数据输入完毕 则退出
			if (inputLine.equals("exit"))
			{
				zaGong.printlnData();
				System.out.println("当前所有工人能领到的工资为：" + zaGong.getSumOfWage());
				System.out.println("当前计算下的总资金为：" + zaGong.getTotalSum() + " * " + MONEYPER + " = "+ (zaGong.getTotalSum()* MONEYPER));
				sc.close();
				break;
			}
			//如果不是，则将当前数据添加到数据库中
			//如果输入成功，则继续
			if (zaGong.addData(inputLine))
			{
				System.out.println("输入成功，请继续，输入exit表示输入结束");
				continue;
			}
			else 
			{
				System.out.println("输入失败，请检查名字和格式'批次 名字 名字 单价'，输入exit表示输入结束");
				continue;
			}
		}
	}
}

class Worker2
{
	private String name;//工人的名字
	private ArrayList<Integer> weightItem = new ArrayList<>();//代表吨位，
	private ArrayList<Integer> numberItem = new ArrayList<>();//第二个代表几个人装
	private ArrayList<Float> priceItem = new ArrayList<>();//单价
	private ArrayList<Double> moneyItem = new ArrayList<>();//每次金钱
	private float money = 0.0f;
	private boolean flag = false;

	//new对象时一定要有名字
	public Worker2(String name)
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
			moneyItem.add( Math.floor((weightItem.get(i) / (numberItem.get(i) * 1.0)) * 100.0) / 100.0 * ZaGong.MONEYPER); //保留一位小数
		}
		
		for (int i = 0; i < moneyItem.size(); i++)
		{
			money +=  moneyItem.get(i);
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
		money = getMoney();
		String returnString = name + ":\t";
		for (var i = 0;(moneyItem.size() != 1) && (i < moneyItem.size()); i++)
		{
			returnString = returnString + moneyItem.get(i);
			if (i != (moneyItem.size() - 1))
			{
				returnString = returnString + "+";
			}
		}
		return returnString + "=\t" + money;
	}
}