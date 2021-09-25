package terpi.objects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;


public class DataPoint<Date , Float>
{
	public LocalDate time;
	public java.lang.Float value;

	public DataPoint(LocalDate time, java.lang.Float value)
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
	public LocalDate getTime()
	{
		return time;
	}

	public void setTime(LocalDate time)
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
