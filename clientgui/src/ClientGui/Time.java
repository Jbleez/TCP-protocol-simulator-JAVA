/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientGui;


public class Time {
	
	private long startTime;
	
	private long stopTime;
	
	public Time()
	{
		
		startTime = 0;
		
		stopTime = 0;
	}
	
	
	public void StartTransmission()
	{
		startTime = System.nanoTime();
	}
	
	public void StopTransmission()
	{
		stopTime = System.nanoTime();
	}
	
	public long TransmissionTime()
	{
		return stopTime - startTime;
	}
	
	public void ResetTime()
	{
		startTime = 0;
		stopTime = 0;
	}
	
	public double CalcBandwidth(int total, long nanoTime)
	{
		return total / (nanoTime / (1.0 * 1000000000.0));
	}
	
	public String toString()
	{
		return "Transmission Time: " + TransmissionTime() + " nanoseconds.";
	}

}

