package _1_recursion_labs;

/**
 * Parser works with (), +, -, *, / (divides only in integers: 2/3=0). Program does not count for spaces.
 */

public class ParserExp {
    public static void main(String[] args) {
        String s = "-312";
        System.out.println(parser(s));
        s = "12*(5-9)";
        System.out.println(parser(s));
        s = "-1*12-5-(3-5)*9";
        System.out.println(parser(s));
        s = "33*((2-3)-7*0+1)-3*(4-1)";
        System.out.println(parser(s));
        s = "(23/23*(-4)/2+(((-1)/1)))";
        System.out.println(parser(s));
    }

    // Works with brackets   :   1st priority
    public static int parser(String calc) {
        boolean rightb = calc.contains(")");
        boolean leftb = calc.contains("(");
        if (!rightb && !leftb) return calcMultDiv(calc);
        else{
            int i=0;
            int posleft=0;
            while (i<calc.length()){
                if ((""+calc.charAt(i)).equals("(")) posleft=i;
                else if ((""+calc.charAt(i)).equals(")")) break;
                i++;
            }
            return parser(calc.substring(0,posleft) + calcMultDiv(calc.substring(posleft+1,i)) + calc.substring(i + 1));
        }
    }

    // Works with * and /   :   2nd priority
    public static int calcMultDiv(String calc){
        if (calc.contains("*") || calc.contains("/")) {
            // find position of * or /
            int signpos = min(calc.indexOf("*"),calc.indexOf("/"));

            // take next number
            int i = signpos;
            String nextnum = "";
            if ((""+calc.charAt(signpos+1)).equals("-")) {
                nextnum = "-";
                i++;
            }
            while (i < calc.length()-1 && Character.isDigit(calc.charAt(i+1))) {
                nextnum+=calc.charAt(++i);
            }
            // take previous number
            int j=signpos;
            String prevnum = "";
            while (j>0 && Character.isDigit(calc.charAt(j-1))) {
                prevnum=calc.charAt(--j)+prevnum;
            }
            // replace 'A*B' by result in a string, and call calcMultDiv again
            String nextcalc;
            if ((""+calc.charAt(signpos)).equals("*")) nextcalc=""+(Integer.parseInt(nextnum)*Integer.parseInt(prevnum));
            else nextcalc=""+(Integer.parseInt(prevnum)/Integer.parseInt(nextnum));
            if (j>0&&i<calc.length()-1) nextcalc=calc.substring(0,j) + nextcalc + calc.substring(i+1);
            else if (j==0&&i<calc.length()-1) nextcalc+=calc.substring(i+1);
            else if (j>0&&i==calc.length()-1) nextcalc=calc.substring(0,j) + nextcalc;

            return calcMultDiv(nextcalc);
        }
        else return calcSumm(clearString(calc));
    }

    // Works with + and -   :  3rd priority
    public static int calcSumm(String calc) {
        // add '+' if a string starts with a digit
        if (Character.isDigit(calc.charAt(0))) {
            calc = "+"+calc;
        }
        // find 1st number in a string (after +/-)
        int i = 0;
        String num = ""+calc.charAt(0);
        while (i < calc.length() - 1 && Character.isDigit(calc.charAt(i+1))) {
            num+=calc.charAt(++i);
        }
        // return the result
        if (i < calc.length() - 1) return Integer.parseInt(num)+calcSumm(calc.substring(++i));
        else return Integer.parseInt(num);
    }

    // Finds minimum, which is not less than zero  (for index received from .indexOf)
    public static int min(int a, int b){
        if (b>-1 && (a>b || a<0)) return b;
        else return a;
    }

    // Transforms a string which includes ++,+-,-+,-- to a string with one sign +/- between the numbers
    public static String clearString (String s){
        int mm = s.indexOf("--");
        int mp = s.indexOf("-+");
        int pm = s.indexOf("+-");
        int pp = s.indexOf("++");
        if(mm>= 0|pm>= 0|mp>= 0|pp>= 0) {
            if (mm >= 0) return clearString(s.substring(0, mm) + "+" + s.substring(mm + 2));
            else if (mp >= 0) return clearString(s.substring(0, mp) + "-" + s.substring(mp + 2));
            else if (pm >= 0) return clearString(s.substring(0, pm) + "-" + s.substring(pm + 2));
            else return clearString(s.substring(0, pp) + "+" + s.substring(pp + 2));
        }
        else return s;
    }
}