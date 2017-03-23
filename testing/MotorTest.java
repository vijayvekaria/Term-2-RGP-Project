package testing;

public class MotorTest 
{
	static RegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
	static RegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.D);
	
	static DifferentialPilot dPilot = new DifferentialPilot(56, 120, leftMotor, rightMotor);
	
	static RegulatedMotor[] syncList = {rightMotor};
	
	Port gyroPort = LocalEV3.get().getPort("S1");
	EV3GyroSensor gyro = new EV3GyroSensor(gyroPort);
	SampleProvider gyroProvider = gyro.getAngleMode();	
	float[] gyroSample = new float[gyroProvider.sampleSize()];
	
	TextLCD screen = LocalEV3.get().getTextLCD();
	 
	public static void main(String[] args) 
	{
		MotorTest test = new MotorTest();
		//test.forwardTest();
	}

	private void rotationTest() 
	{
		for(int i = 0; i < 4; i++)
// 		{
// //			leftMotor.setSpeed(102); //(614/2) which is 30cm per 2 seconds
// //			rightMotor.setSpeed(102);
// //			
// //			leftMotor.synchronizeWith(syncList);
// //			leftMotor.startSynchronization();
// //			
// //			leftMotor.forward();
// //			rightMotor.forward();
// //			
// //			leftMotor.endSynchronization();
// //			
// //			try {
// //				Thread.sleep(2000);
// //			} catch (InterruptedException e) {
// //				// TODO Auto-generated catch block
// //				e.printStackTrace();
// //			}
// //			
// //			leftMotor.synchronizeWith(syncList);
// //			leftMotor.startSynchronization();
// //			
// //			leftMotor.stop();
// //			rightMotor.stop();
// //			
// //			leftMotor.endSynchronization();
			
			rotate(1);
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		}
	}

	public void rotate(int direction) {
		
		gyro.reset();
		
		leftMotor.setSpeed(40);
		rightMotor.setSpeed(40);
		
		leftMotor.synchronizeWith(syncList);
		leftMotor.startSynchronization();
		
		gyroProvider.fetchSample(gyroSample, 0);
		
		if (direction == 1)//CLOWCKWISE
		{ 
			leftMotor.forward();
			rightMotor.backward();
		} 
		else  //ANTICLOCKWISE
		{
			leftMotor.backward();
			rightMotor.forward();
		}
		
		leftMotor.endSynchronization();
		
		if (direction == 1)
		{
			while(gyroSample[0] <= 87)
			{
				screen.drawString(gyroSample[0] + "", 0, 1);
				gyroProvider.fetchSample(gyroSample, 0);
			}
		} 
		else 
		{
			while(gyroSample[0] >= -87)
			{
				screen.drawString(gyroSample[0] + "", 0, 1);
				gyroProvider.fetchSample(gyroSample, 0);
			}
		}

		leftMotor.synchronizeWith(syncList);
		leftMotor.startSynchronization();
		
		leftMotor.stop();
		rightMotor.stop();
		
		leftMotor.endSynchronization();
	}

	public void forwardTest() {
		
		leftMotor.setSpeed(41); //2cm per second
		rightMotor.setSpeed(41);
		
		for(int i = 0; i < 10; i++)
		{
			leftMotor.synchronizeWith(syncList);
			leftMotor.startSynchronization();
			
			leftMotor.forward();
			rightMotor.forward();
			
			leftMotor.endSynchronization();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			leftMotor.startSynchronization();
			
			leftMotor.stop();
			rightMotor.stop();
			
			leftMotor.endSynchronization();
			
			try {
				Thread.sleep(8000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
