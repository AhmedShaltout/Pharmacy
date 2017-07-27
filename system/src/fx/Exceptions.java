package fx;

import javax.swing.JOptionPane;

abstract class Exceptions {
	static boolean isString(String word) throws Exception{
		if(word!=null&&!word.equals("")){
			for(int y=0;y<word.length();y++ ){
				if(word.charAt(y)!=' '){
					return true;
				}
			}
		}
		throw new Exception("Invalid Text : "+word+"");
	}
	static boolean isNumber(String number) throws Exception{
		try{
			Integer.parseInt(number);
			return true;
		}catch(Exception e){
			throw new Exception("Invalid Number : "+number+"");
		}
	}
	static boolean isInRange(String number) throws Exception{
		int x=Integer.parseInt(number);
		if(x<255&&x>0)
			return true;
		throw new Exception("Invalid Number : "+number+"");
	}
	public static void isFloat(String text) throws Exception {
		try{
			Float x=Float.parseFloat(text);
			if(x<0)
				throw new Exception("Invalid Price : "+text+"");
		}catch(Exception e){
			throw new Exception("Invalid Price : "+text+"");
		}
	}
	public static void invalidInputWarning(String message) {
		JOptionPane.showMessageDialog(null, message,"ERROR", JOptionPane.WARNING_MESSAGE);
	}
	public static void success(String string) {
		JOptionPane.showMessageDialog(null, string, "DONE",JOptionPane.YES_OPTION);
	}
}
