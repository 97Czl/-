import java.util.*;

public class ZhuangChe
{
	//ÿ������
	private String[] names = {"��", "��", "��", "��", "С��", "����", "С��", "��", "��", "����", "����", "ë��", "��"};
	//ÿ�ֵĵ���
	public static float MONEYPERTON = 6.5f;
	//ÿ�ն�λ
	private ArrayList<Float> todayWeight = new ArrayList<>();
	//ÿ���˵Ķ���
	private Worker1[] workers = new Worker1[names.length];

	//��ʼ�����ж���
	public void init()
	{
		for (int i = 0; i < workers.length; i++)
		{
			workers[i] = new Worker1(names[i]);
		}
	}

	//���������string�������������
	public boolean addData(String input)
	{
		String[] current = input.split(" ");

		//�����λ
		float first;
		try
		{
			first = Float.parseFloat(current[0]);
		}
		catch (Exception e)
		{
			System.out.println("����ת������");
			return false;
		}

		//����ÿһ��������ֵ
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
			System.out.println("������������");
			return false;
		}
		//��Ӷ�λ
		todayWeight.add(first);
		//��ÿ�����������
		for (int i = 1; i < current.length; i++)
		{
			workers[temIndex[i - 1]].addItem(first, (current.length - 1));
		}
		return true;
	}

	//���ؽ����ܶ�λ
	public float totalWeight()
	{
		float sum = 0;
		for (int i = 0; i < todayWeight.size(); i++)
		{
			sum = sum + todayWeight.get(i);
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

	//����������ݺͶ�λ
	public void printlnData()
	{
		for (var ele : workers)
		{
			System.out.println(ele);
		}
		System.out.println("�ܶ�λΪ��" + totalWeight());
		float totalMoney = (float)(totalWeight() * MONEYPERTON);
		float totalOfWokers = 0.0f;
		float diff = 0.0f;
		for (var ele : workers)
		{
			totalOfWokers = totalOfWokers + ele.getMoney();
		}
		totalOfWokers = (float) (Math.round(totalOfWokers * 10.0f) / 10.0);
		System.out.println("����" + totalWeight() + "�ֵ���ǮΪ��" + totalMoney);
		System.out.println("���칤�����쵽��Ǯ��ǮΪ��" + totalOfWokers);
		diff = (float)Math.round((totalMoney - totalOfWokers) * 10.0f) / 10.0f;
		System.out.println("����Ǯ�Ĳ�ֵΪ��" + diff);
		System.out.println("----------------����ר�����ݣ�-------------------------------------");
		for (var ele : workers)
		{
			System.out.println(ele.getMoney());
		}

	}

	public static void main(String[] args)
	{
		ZhuangChe zhuangChe = new ZhuangChe();
		//��ʼ����������
		zhuangChe.init();
		System.out.println("���Ǽ���װ����-------����" + MONEYPERTON + "-------------��������С����λ");
		System.out.println("����'��λ ���� ����'����ʽ�������ݣ�����exit��ʾ�������");
		Scanner sc = new Scanner(System.in);
		while (true)
		{
			String inputLine = sc.nextLine();
			//�������exit��������������������� ���˳�
			if (inputLine.equals("exit"))
			{
				zhuangChe.printlnData();
				sc.close();
				break;
			}
			//������ǣ��򽫵�ǰ������ӵ����ݿ���
			//�������ɹ��������
			if (zhuangChe.addData(inputLine))
			{
				System.out.println("����ɹ��������������exit��ʾ�������");
				continue;
			}
			else
			{
				System.out.println("����ʧ�ܣ��������ֺ͸�ʽ'��λ ���� ����'������exit��ʾ�������");
				continue;
			}
		}
	}
}

class Worker1
{
	private String name;//���˵�����
	private ArrayList<Float> weightItem = new ArrayList<>();//�����λ��
	private ArrayList<Integer> numberItem = new ArrayList<>();//�ڶ�����������װ
	private float money = 0.0f;
	private boolean flag = false;

	//new����ʱһ��Ҫ������
	public Worker1(String name)
	{
		this.name = name;
	}

	//�������������
	public void addItem(float weight, int numOfPeople)
	{
		weightItem.add(weight);
		numberItem.add(numOfPeople);
	}

	private void updateMoney()
	{
		for (int i = 0; i < weightItem.size(); i++)
		{
			money += Math.floor((weightItem.get(i) * ZhuangChe.MONEYPERTON / (numberItem.get(i) * 1.0))* 10.0) / 10.0; //����һλС��
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
		return name + "\t" + getMoney();
	}
}