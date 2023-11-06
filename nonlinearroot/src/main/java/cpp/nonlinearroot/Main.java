package cpp.nonlinearroot;

//Bisection, Newton-Raphson, Secant, False-Position

public class Main {
    public static void main(String[] args) 
    {
       Bisection(0, 1, 0.01, 100, true);

       System.out.println("\n Newton \n");

       Newton(4, 0.01, 0.0001, 100, true);

       System.out.println("\n Secant \n");

       Secant(0, 1, 0.01, 100, true);

       System.out.println("\n False Position \n");

       FalsePosition(1, 3, 0.01, 100, true);
    }

    public static void FalsePosition(double a, double b, double epsilon, int nMAX, boolean runEQOne)
    {
        double fa = Equations(a, runEQOne);
        double fb = Equations(b, runEQOne);
        double fc;

        if( !((fa >= 0) ^ (fb >= 0)) ) // aPositive? XOR bPositive?
        {
            System.out.print("A: ");
            System.out.print(a);
            System.out.print(" F(a): ");
            System.out.println(fa);
            System.out.print("B: ");
            System.out.print(b);
            System.out.print(" F(b): ");
            System.out.println(fb);
            System.out.println("Function Has No Local Root. Same Sign.");
            return;
        }

        for (int n = 0; n < nMAX; n++) 
        {
            
            double c = ((a * fb) - (b * fa)) / (fb - fa);
            fc = Equations(c, runEQOne);

            if( fc == 0 || Math.abs(b-a) < epsilon)
            {
                System.out.println("Convergence");
                return;
            }

            if( (fc * fa) < 0 )
            {
                b = c;
            }
            else
            {
                a = c;
            }
            System.out.print("N: ");
            System.out.println(n);
            System.out.print("C: ");
            System.out.println(c);
            System.out.print("F(c):");
            System.out.println(fc);

        }
    }

    public static void Bisection(double a, double b, double epsilon, int nMAX, boolean runEQOne)
    {
        double fa = Equations(a, runEQOne);
        double fb = Equations(b, runEQOne);
        double fc;
        double error = b - a;

        if( !((fa >= 0) ^ (fb >= 0)) ) // aPositive? XOR bPositive?
        {
            System.out.print("A: ");
            System.out.print(a);
            System.out.print(" F(a): ");
            System.out.println(fa);
            System.out.print("B: ");
            System.out.print(b);
            System.out.print(" F(b): ");
            System.out.println(fb);
            System.out.println("Function Has Not Local Root. Same Sign.");
            return;
        }

        for (int n = 0; n < nMAX; n++) 
        {
            error = error / 2;
            double c = a + error;
            fc = Equations(c, runEQOne);

            System.out.print("N: ");
            System.out.println(n);
            System.out.print("C: ");
            System.out.println(c);
            System.out.print("F(c):");
            System.out.println(fc);
            System.out.print("Error: ");
            System.out.println(error);

            if( Math.abs(error) < epsilon )
            {
                System.out.println("Convergence");
                return;
            }

            if( ((fa >= 0) ^ (fc >= 0)) )
            {
                b = c;
                fb = fc;
            }
            else
            {
                a = c;
                fa = fc;
            }

        }
    }

    public static void Newton(double x, double epsilon, double phi, int nMax, boolean runEQOne)
    {
        double fx = Equations(x, runEQOne);

        System.out.print(0);
        System.out.print(" - x: ");
        System.out.println(x);
        System.out.print(" - F(x): ");
        System.out.println(fx);

        for (int n = 0; n < nMax; n++) 
        {
            double fp = DerivativeEquations(x, runEQOne);
            if(Math.abs(fp) < phi)
            {
                System.out.println("Derivite Value TOO SMALL");
                return;
            }    

            double div = fx/fp;
            x = x-div;
            fx = Equations(x, runEQOne);

            System.out.print("n: ");
            System.out.println(n);
            System.out.print("x: ");
            System.out.println(x);
            System.out.print("F(x): ");
            System.out.println(fx);

            if(Math.abs(div) < epsilon)
            {
                System.out.println("Convergence");
                return;
            }
        }
    }

    public static void Secant(double a, double b, double epsilon, int nMax, boolean runEQOne)
    {
        double fa = Equations(a, runEQOne);
        double fb = Equations(b, runEQOne);

        if( Math.abs(fa) > Math.abs(fb) )
        {
            double temp = a;
            a = b;
            b = temp;

            temp = fa;
            fa = fb;
            fb = temp;
        }

        System.out.print("0 ");
        System.out.print(" a: ");
        System.out.println(a);
        System.out.print("  F(a): ");
        System.out.println(fa);

        System.out.print("1 ");
        System.out.print(" b: ");
        System.out.println(b);
        System.out.print("   F(b): ");
        System.out.println(fb);


        for (int n = 2; n < nMax; n++) 
        {
            if( Math.abs(fa) > Math.abs(fb) )
            {
                double temp = a;
                a = b;
                b = temp;

                temp = fa;
                fa = fb;
                fb = temp;
            }

            double div = (b-a) / (fb-fa);
            b = a;
            fb = fa;
            div = div * fa;

            if(Math.abs(div) < epsilon)
            {
                System.out.println("Convergence");
                return;
            }

            a = a-div;
            fa = Equations(a, runEQOne);

            System.out.print("N: ");
            System.out.println(n);
            System.out.print("A: ");
            System.out.println(a);
            System.out.print("F(a): ");
            System.out.println(fa);

        }


    }

    public static double Equations(double x, boolean one)
    {
        if(one)
            return (2* Math.pow(x, 3)) - (11.7 * Math.pow(x, 2)) + (17.7*x) - 5; 
        else
            return x + 10 - (x * Math.cosh(50/x)); 
    }

    public static double DerivativeEquations(double x, boolean one)
    {
        if(one)
            return ( (60 * Math.pow(x, 2)) - (234 * x) + 177  ) / 10; 
        else
            return ((50 * Math.sinh(50/x)) / x) - (Math.cosh(50/x)) + 1; 
    }


}