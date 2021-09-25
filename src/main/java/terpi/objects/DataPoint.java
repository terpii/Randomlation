package terpi.objects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DataPoint<Date , Float>
{
	public Date time;
	public java.lang.Float value;

	public DataPoint(Date time, java.lang.Float value)
	{
		this.time = time;
		this.value = value;
	}

	public String toString()
	{
		DateFormat df = new SimpleDateFormat("MMMM yyyy");
		return "[" + df.format(time) + " : " + value.toString() + "]";
	}


	//getters and setters
	public Date getTime()
	{
		return time;
	}

	public void setTime(Date time)
	{
		this.time = time;
	}

	public java.lang.Float getValue()
	{
		return value;
	}

	public void setValue(java.lang.Float value)
	{
		this.value = value;
	}
}
