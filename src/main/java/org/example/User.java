package org.example;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.Scanner;

public class User {


    public static void main(String[] args) throws Exception,ClassNotFoundException {
        try {
            Socket s = new Socket("localhost", 8000);
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());

            int [][] m1,m2,rslt;
            String username, password;
            m1=new int [5][5];
            m2=new int [5][5];
            boolean auth;
            String m;
            Auth user = null;
            //scanner
            Scanner sc=new Scanner(System.in);

            //credential
            do {
                System.out.println("Enter your username: ");
                username = sc.nextLine();
                System.out.println("Enter your password: ");
                password = sc.nextLine();
                user = new Auth(username, password);
                //send credential
                out.writeObject(user);
                auth = (boolean) in.readObject();
                m=(String) in.readObject();
                System.out.println(m);
            }while(!auth);

            m=(String) in.readObject();
            System.out.println(m);

            int n;
            n=sc.nextInt();
            out.writeObject(n);

            int num=(int) in.readObject();


            for (int k=0;k<num;k++) {
                System.out.println("enter matrix  :" +k);
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        m1[i][j] = sc.nextInt();
                    }
                }
                out.writeObject(m1);
            }

            //result
            rslt=new int [5][5];
            rslt=(int [][])in.readObject();

            System.out.println("result  :");
            for (int i=0;i<2;i++){
                System.out.println("");
                for (int j=0;j<2;j++){
                    System.out.print(rslt[i][j]+"   ");
                }
            }

            in.close();
            out.close();
            s.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
