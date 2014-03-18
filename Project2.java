public static void main (String [] args) throws FileNotFoundException
	{
		File aFile  = new File("SampleLottoData.txt");
		if (!aFile.exists())
			System.out.println("Cannot find file");
		else
		{
			Scanner in = new Scanner(aFile);
			String aLineFromFile, date = "";
			ArrayList<String> list1 = new ArrayList<String>();
			int [] jpnums = new int [45];
			int [] bnums = new int [45];
			int [] x = new int [3];
			int day = 0;
			String year = "";
			while (in.hasNext())
			{
				aLineFromFile = in.nextLine();
				list1.addAll(Arrays.asList(aLineFromFile.split(",")));
			}
			in.close();
			for (int i = 0; i < list1.size()/9; i++)
			{
				date = list1.get(i);
				x [0] = Integer.parseInt(date.substring(0,2));
				x [1] = Integer.parseInt(date.substring(3,5));
				x [2] = Integer.parseInt(date.substring(6,10));
				day = dayCheck(x);
				if (Integer.parseInt(args[2]) != 0 || (Integer.parseInt(args[2]) != 1 && day != 4) || (Integer.parseInt(args[2]) != 2 && day != 0))
				{
					int q = i;
					for (int e = 0; e < 9; e++)
					{
						list1.remove(q);
						q++;
					}
				}
				i += 8;
			}
			
		
		int p = 8;
		for (int i = 0; i < ((list1.size())/9); i++)
		{
			
			if (args[1].matches(list1.get(p)))
			{
				for (int e = 0; e < 8; e++)
					{
						if(list1.size() != 0)
						{
							list1.remove(p);
							p--;
						}
					}
			}
			p += 8; 
		}
		if (args.length == 4)
		{
			for (int i = 0; i < list1.size()/9; i++)
			{
				date = list1.get(i);
				year = date.substring(6,10);
				
				if (year != args[3])
				{
					int q = i;
					for (int e = 0; e < 9; e++)
					{
						list1.remove(q);
						q++;
					}
				}
				i += 8;
			}
		}
		String pattern = "[0-9]+";
		
		for (int i = 1; i < jpnums.length; i++)
		{
			if (list1.get(i).matches(pattern))
			{
				Integer.parseInt(list1.get(i));
				jpnums[i-1]++;
			}
			
		}
		int v = 1;
		for (int i = 0; i < jpnums.length; i++)
		{
			System.out.println(v+" occurs "+jpnums[i]+" times");
			v++;
		}
	}
}
	
	public static Integer dayCheck(int [] x)
	{
		int a, b, d, m, y, result;
		d = x[0]; 
		m = x[1];
		y = x[2];
		if (m == 1 || m == 2)
			{
			m += 12; y -=  1;
		}
		a = y % 100;  b = y / 100;
		result = ((d + (((m + 1)*26)/10)+ a + (a/4) + (b/4)) + (5*b)) % 7;
		return result;     
	}
