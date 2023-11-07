package cpp.nonlinearroot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//Bisection, Newton-Raphson, Secant, False-Position

public class Main {

    private static boolean argsCheck(String[] args)
    {
        if(args.length < 5)
            return false;

        try 
        {
            for (int i = 0; i < args.length-2; i++) 
            {
                Double.parseDouble(args[0]);    
            }
        } 
        catch (NumberFormatException e) 
        {
            return false;
        }
        
        return true;
    }

    private static String fileTemplateName;

    public static void main(String[] args) 
    {
        if(!argsCheck(args))
        {
            System.err.println("Invalid Arguments");
            return;
        }

        double a = Double.parseDouble(args[0]);
        double b = Double.parseDouble(args[1]);
        double epsilon = Double.parseDouble(args[2]);
        boolean runEQOne = Boolean.parseBoolean(args[3]);
        fileTemplateName = args[4];

        Bisection(a, b, epsilon, 100, runEQOne);

        System.out.println("\n Newton \n");

        Newton(a, epsilon, 0.00000001, 100, runEQOne);

        System.out.println("\n Secant \n");

        Secant(a, b, epsilon, 100, runEQOne);

        System.out.println("\n False Position \n");

        FalsePosition(a, b, epsilon, 100, runEQOne);
    }

    private static void FalsePosition(double a, double b, double epsilon, int nMAX, boolean runEQOne)
    {
        double fa = Equations(a, runEQOne);
        double fb = Equations(b, runEQOne);
        double fc;

        FileWriter writer = null;
        try
        {
            File file = new File(fileTemplateName.concat("FALSEPOSITION.txt"));
            if(file.exists())
                file.delete();
            file.createNewFile();
            writer = new FileWriter(file);
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
        StringBuilder SB = new StringBuilder();
        SB.append("ITERATION\t");
        SB.append("ERROR\t");
        SB.append("A\t");
        SB.append("F(A)\t");
        SB.append("B\t");
        SB.append("F(B)\t");
        SB.append("C\t");
        SB.append("F(C)\t\n");
        try {
            writer.write( SB.toString() + "\n" );
        } catch (IOException e) {
            e.printStackTrace();
        }

        if( !((fa >= 0) ^ (fb >= 0)) ) // aPositive? XOR bPositive?
        {
            System.out.print("A: ");System.out.print(a);System.out.print(" F(a): ");System.out.println(fa);System.out.print("B: ");System.out.print(b);System.out.print(" F(b): ");System.out.println(fb);System.out.println("Function Has No Local Root. Same Sign.");
            return;
        }

        for (int n = 0; n < nMAX; n++) 
        {
            double c = ((a * fb) - (b * fa)) / (fb - fa);
            fc = Equations(c, runEQOne);

            SB = new StringBuilder();
            SB.append(String.valueOf(n)+ "\t");
            SB.append(String.valueOf(Math.abs(b-a))+ "\t");
            SB.append(String.valueOf(a)+ "\t");
            SB.append(String.valueOf(fa)+ "\t");
            SB.append(String.valueOf(b)+ "\t");
            SB.append(String.valueOf(fb)+ "\t");
            SB.append(String.valueOf(c)+ "\t");
            SB.append(String.valueOf(fc)+ "\t");
            try {
                writer.write( SB.toString() + "\n" );
            } catch (IOException e) {
                e.printStackTrace();
            }

            if( fc == 0 || Math.abs(b-a) <= epsilon)
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
            System.out.print("N: ");System.out.println(n);System.out.print("C: ");System.out.println(c); System.out.print("F(c):");System.out.println(fc);
        }

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void Bisection(double a, double b, double epsilon, int nMAX, boolean runEQOne)
    {
        double fa = Equations(a, runEQOne);
        double fb = Equations(b, runEQOne);
        double fc;
        double error = b - a;

        FileWriter writer = null;
        try
        {
            File file = new File(fileTemplateName.concat("BISECTION.txt"));
            if(file.exists())
                file.delete();
            file.createNewFile();
            writer = new FileWriter(file);
        } catch (IOException e) 
        {
            e.printStackTrace();
        }

        StringBuilder SB = new StringBuilder();
        SB.append("ITERATION\t");
        SB.append("ERROR\t");
        SB.append("A\t");
        SB.append("F(A)\t");
        SB.append("B\t");
        SB.append("F(B)\t");
        SB.append("C\t");
        SB.append("F(C)\t\n");
        try {
            writer.write( SB.toString() + "\n" );
        } catch (IOException e) {
            e.printStackTrace();
        }

        if( !((fa >= 0) ^ (fb >= 0)) ) // aPositive? XOR bPositive?
        {
            System.out.print("A: ");System.out.print(a);System.out.print(" F(a): ");System.out.println(fa);System.out.print("B: ");System.out.print(b);System.out.print(" F(b): ");System.out.println(fb);System.out.println("Function Has Not Local Root. Same Sign.");
            
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            return;
        }

        for (int n = 0; n < nMAX; n++) 
        {
            error = error / 2;
            double c = a + error;
            fc = Equations(c, runEQOne);
            System.out.print("N: ");System.out.println(n);System.out.print("C: ");System.out.println(c);System.out.print("F(c):");System.out.println(fc);System.out.print("Error: ");System.out.println(error);

            SB = new StringBuilder();
            SB.append(String.valueOf(n)+ "\t");
            SB.append(String.valueOf(Math.abs(error))+ "\t");
            SB.append(String.valueOf(a)+ "\t");
            SB.append(String.valueOf(fa)+ "\t");
            SB.append(String.valueOf(b)+ "\t");
            SB.append(String.valueOf(fb)+ "\t");
            SB.append(String.valueOf(c)+ "\t");
            SB.append(String.valueOf(fc)+ "\t");
            try {
                writer.write( SB.toString() + "\n" );
            } catch (IOException e) {
                e.printStackTrace();
            }

            if( Math.abs(error) <= epsilon )
            {
                System.out.println("Convergence");

                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

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

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void Newton(double x, double epsilon, double phi, int nMax, boolean runEQOne)
    {
        double fx = Equations(x, runEQOne);

        System.out.print(0);System.out.print(" - x: ");System.out.println(x);System.out.print(" - F(x): ");System.out.println(fx);

        FileWriter writer = null;
        try
        {
            File file = new File(fileTemplateName.concat("NEWTON.txt"));
            if(file.exists())
                file.delete();
            file.createNewFile();
            writer = new FileWriter(file);
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
        StringBuilder SB = new StringBuilder();
        SB.append("ITERATION\t");
        SB.append("ERROR\t");
        SB.append("A\t");
        SB.append("F(A)\t");
        SB.append("B\t");
        SB.append("F(B)\t");
        SB.append("C\t");
        SB.append("F(C)\t\n");
        try {
            writer.write( SB.toString() + "\n" );
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int n = 0; n < nMax; n++) 
        {
            double fp = DerivativeEquations(x, runEQOne);
            if(Math.abs(fp) < phi)
            {
                System.out.println("Derivite Value TOO SMALL");
                
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return;
            }   

            double div = fx/fp;

            SB = new StringBuilder();
            SB.append(String.valueOf(n)+ "\t");
            SB.append(String.valueOf(Math.abs(div))+ "\t");
            SB.append(String.valueOf(x)+ "\t");
            SB.append(String.valueOf(fx)+ "\t");
            SB.append(String.valueOf(x)+ "\t");
            SB.append(String.valueOf(fp)+ "\t");
            SB.append(" "+ "\t");
            SB.append(" "+ "\t");
            try {
                writer.write( SB.toString() + "\n" );
            } catch (IOException e) {
                e.printStackTrace();
            }

            x = x-div;
            fx = Equations(x, runEQOne);

            System.out.print("n: ");System.out.println(n);System.out.print("x: ");System.out.println(x);System.out.print("F(x): ");System.out.println(fx);

            if(Math.abs(div) <= epsilon)
            {
                System.out.println("Convergence");

                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return;
            }
        }

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void Secant(double a, double b, double epsilon, int nMax, boolean runEQOne)
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

        FileWriter writer = null;
        try
        {
            File file = new File(fileTemplateName.concat("SECANT.txt"));
            if(file.exists())
                file.delete();
            file.createNewFile();

            writer = new FileWriter(file);
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
        StringBuilder SB = new StringBuilder();
        SB.append("ITERATION\t");
        SB.append("ERROR\t");
        SB.append("A\t");
        SB.append("F(A)\t");
        SB.append("B\t");
        SB.append("F(B)\t");
        SB.append("C\t");
        SB.append("F(C)\t\n");
        try {
            writer.write( SB.toString() + "\n" );
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print("0 ");System.out.print(" a: ");System.out.println(a);System.out.print("  F(a): ");System.out.println(fa);System.out.print("1 ");System.out.print(" b: ");System.out.println(b);System.out.print("   F(b): ");System.out.println(fb);

        for (int n = 0; n < nMax; n++) 
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

            if(Math.abs(div) <= epsilon)
            {
                System.out.println("Convergence");
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }

            a = a-div;
            fa = Equations(a, runEQOne);

            System.out.print("N: ");System.out.println(n);System.out.print("A: ");System.out.println(a);System.out.print("F(a): ");System.out.println(fa);

            SB = new StringBuilder();
            SB.append(String.valueOf(n)+ "\t");
            SB.append(String.valueOf(Math.abs(div))+ "\t");
            SB.append(String.valueOf(a)+ "\t");
            SB.append(String.valueOf(fa)+ "\t");
            SB.append(String.valueOf(b)+ "\t");
            SB.append(String.valueOf(fb)+ "\t");
            SB.append(" "+ "\t");
            SB.append(" "+ "\t");
            try {
                writer.write( SB.toString() + "\n" );
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static double Equations(double x, boolean one)
    {
        if(one)
            return (2* Math.pow(x, 3)) - (11.7 * Math.pow(x, 2)) + (17.7*x) - 5; 
        else
            return x + 10 - (x * Math.cosh(50/x)); 
    }

    private static double DerivativeEquations(double x, boolean one)
    {
        if(one)
            return ( (60 * Math.pow(x, 2)) - (234 * x) + 177  ) / 10; 
        else
            return ((50 * Math.sinh(50/x)) / x) - (Math.cosh(50/x)) + 1; 
    }


}