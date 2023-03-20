package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class myThread extends Thread implements Serializable{

    private Socket socket;
    private ArrayList<Auth> users;
    public myThread(Socket socket,ArrayList<Auth> users) {
        this.socket = socket;
        this.users=users;
    }
    int [][] m1,m2,rslt;

    @Override
    public void run() {

        m1=new int [5][5];
        m2=new int [5][5];
        rslt=new int [5][5];
        boolean auth = false;
        String message = "";

        try {
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

            do {
                //reading credential
                Auth user = (Auth) input.readObject();

                //check credential
                for (int i = 0; i < users.size(); i++) {
                    if (Objects.equals(users.get(i).userName, user.userName) && Objects.equals(users.get(i).password, user.password)) {
                        auth = true;
                        message = "Successfully authorized";
                        i = users.size() + 1;
                    } else {
                        auth = false;
                        message = "Incorrect credentials try again";
                    }
                }
                output.writeObject(auth);
                output.writeObject(message);
                System.out.println(message);
            }while(!auth);

            if(auth) {


                output.writeObject("Please choose an operation:\n" +
                        "1-Sum of two matrices\n" +
                        "2-Multiplication of two matrices\n" +
                        "3-reverse the matrix");
                int n = (int) input.readObject();
                if(n==3){
                    output.writeObject(1);
                    m1 = (int[][]) input.readObject();
                    output.writeObject(reverse(m1));
                }else{
                    output.writeObject(2);
                    m1 = (int[][]) input.readObject();
                    m2 = (int[][]) input.readObject();
                    if (n == 1) {
                        output.writeObject(sum(m1, m2));
                    } else if (n == 2) {
                        output.writeObject(multiple(m1, m2));
                    }
                }



            }

            input.close();
            output.close();
            socket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    //sum of two matrix
    public int[][] sum(int[][] m1,int[][] m2){
        for (int i=0;i<2;i++){
            for (int j=0;j<2;j++){
                rslt[i][j]=m1[i][j]+m2[i][j];
            }
        }
        return rslt;
    }

    //multiple of two matrix
    public int[][] multiple(int[][] m1, int[][] m2){
        for (int i=0;i<2;i++){
            for (int j=0;j<2;j++){
                int sum = 0;
                for (int k=0; k<2; k++){
                    sum += m1[i][k] * m2[k][j];
                }
                rslt[i][j] = sum;
            }
        }
        return rslt;
    }
    public int[][] reverse(int[][] m1){
        int [][]m=new int[2][2];

        for (int i=0;i<2;i++){
            for (int j=0;j<2;j++){
                m[i][j]=m1[j][i];
            }
        }
        rslt = m;
        return rslt;
    }

}
