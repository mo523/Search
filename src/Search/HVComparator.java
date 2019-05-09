package Search;

import java.util.Comparator;

public class HVComparator implements Comparator<INode>
{
	@Override
	public int compare(INode i1, INode i2)
	{
		 return (int) (i1.getHValue() - i2.getHValue());
	}
}
