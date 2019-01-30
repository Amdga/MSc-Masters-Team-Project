
public class Card {
	private String cardname;
	private int val1;
	private int val2;
	private int val3;
	private int val4;
	private int val5;
	
	
 public  Card(String cardname, int a, int b, int c, int d, int e) {
	 this.cardname = cardname;
	 val1 = a;
	 val2 = b;
	 val3 = c;
	 val4 = d;
	 val5 = e;
	
 }
 public String getCardName() {
	 return cardname;
 }
 public int getVal1() {
	 return val1;
 }
 public int getVal2(){
	 return val2;
 }
 public int getVal3() {
	 return val3;
 }
 public int getVal4() {
	 return val4;
 }
 public int getVal5() {
	 return val5;
 }
 public void setCardName(String b) {
	 cardname = b;
 }
 public void setVal1(int a) {
	 val1 = a;
 }
 public void setVal2(int a) {
	 val2 = a;
 }
 public void setVal3(int a) {
	 val3 = a;
 }
 public void setVal4(int a) {
	 val4 = a;
 }
 public void setVal5(int a) {
	 val5 = a;
 }
}

