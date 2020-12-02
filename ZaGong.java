import java.util.*;

public class ZaGong
{
	//ÿ������
	private String[] names = {"��", "��", "��", "��", "С��", "����", "С��", "��", "��", "����", "����", "ë��", "��"};
	//ÿ�ݹ��ĵ���
	public static float MONEYPER = 50;
	//���ʽ�
	private ArrayList<Integer> totalSum = new ArrayList<>();
	//ÿ���˵Ķ���
	private Worker2[] workers = new Worker2[names.length];

	//��ʼ�����ж���
	public void init()
	{
		for (int i = 0; i < workers.length; i++)
		{
			workers[i] = new Worker2(names[i]);
		}
	}

	//���������string�������������
	public boolean addData(String input)
	{
		String[] current = input.split(" ");

		//�����λ
		int first;
		try
		{
			first = Integer.parseInt(current[0]);
		}
		catch (Exception e)
		{
			System.out.println("����ת������");
			return false;
		} 

		//��Ӷ�λ
		totalSum.add(first);
		
		//����ÿһ��������ֵ
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
			System.out.println("������������");
			return false;
		}
		//��ÿ�����������
		for (int i = 0; i < temIndex.length; i++)
		{
			workers[temIndex[i]].addItem(first, (current.length - 1));
		}
		return true;
	}

	//�������ʽ�
	public int getTotalSum()
	{
		int sum = 0;
		for (int i = 0; i < totalSum.size(); i++)
		{
			sum = sum + totalSum.get(i);
		}
		return sum;
	}
	
	//��������ֵ
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

	//�������й��˹���
	private float getSumOfWage()
	{
		float sum = 0.0f;
		for (int i = 0; i < workers.length; i++)
		{
			sum = sum + workers[i].getMoney();
		}
		return sum;
	}

	//����������ݺͶ�λ
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
		//��ʼ����������
		zaGong.init();
		System.out.println("���Ǽ����ӹ���-------����" + MONEYPER);
		System.out.println("����'���� ���� ����'����ʽ�������ݣ�����exit��ʾ�������");
		Scanner sc = new Scanner(System.in);
		while (true)
		{
			String inputLine = sc.nextLine();
			//�������exit��������������������� ���˳�
			if (inputLine.equals("exit"))
			{
				zaGong.printlnData();
				System.out.println("��ǰ���й������쵽�Ĺ���Ϊ��" + zaGong.getSumOfWage());
				System.out.println("��ǰ�����µ����ʽ�Ϊ��" + zaGong.getTotalSum() + " * " + MONEYPER + " = "+ (zaGong.getTotalSum()* MONEYPER));
				sc.close();
				break;
			}
			//������ǣ��򽫵�ǰ������ӵ����ݿ���
			//�������ɹ��������
			if (zaGong.addData(inputLine))
			{
				System.out.println("����ɹ��������������exit��ʾ�������");
				continue;
			}
			else 
			{
				System.out.println("����ʧ�ܣ��������ֺ͸�ʽ'���� ���� ���� ����'������exit��ʾ�������");
				continue;
			}
		}
	}
}

class Worker2
{
	private String name;//���˵�����
	private ArrayList<Integer> weightItem = new ArrayList<>();//�����λ��
	private ArrayList<Integer> numberItem = new ArrayList<>();//�ڶ�����������װ
	private ArrayList<Float> priceItem = new ArrayList<>();//����
	private ArrayList<Double> moneyItem = new ArrayList<>();//ÿ�ν�Ǯ
	private float money = 0.0f;
	private boolean flag = false;

	//new����ʱһ��Ҫ������
	public Worker2(String name)
	{
		this.name = name;
	}

	//�������������
	public void addItem(int weight, int numOfPeople)
	{
		weightItem.add(weight);
		numberItem.add(numOfPeople);
	}
	
	private void updateMoney()
	{
		for (int i = 0; i < weightItem.size(); i++)
		{
			moneyItem.add( Math.floor((weightItem.get(i) / (numberItem.get(i) * 1.0)) * 100.0) / 100.0 * ZaGong.MONEYPER); //����һλС��
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

	//toString ����
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