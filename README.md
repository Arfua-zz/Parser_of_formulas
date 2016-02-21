# Parser_of_formulas

Parser_of_formulas is written in Java.
It takes formulas created in string format as an input, transfers them into numbers and symbols, and calculates the result.
Concretely, parser works with
- (), +, -, *, / 
- any number of brackets, e.g. "(23/23*(-4)/2+(((-1)/1)))" returns -3.
- any number of + and -, e.g. "2+(-(-2))" returns 4
- priorities (1st priority: brackets; 2nd priority: * and / ; 3rd priority: + and -)
