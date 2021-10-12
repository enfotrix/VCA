package com.devdiv.vca;


public class predictor {

    String weather,pitch,ground,oTeam;
    int wp,pp,gp,op;
    int spin,fast,bat;
    String conditions;
    int nBAT,nALL,nASP,nBWL,nSPN;

    public predictor(String weather, String pitch, String ground, String oTeam) {
        this.weather = weather;
        this.pitch = pitch;
        this.ground = ground;
        this.oTeam = oTeam;

        setPitch();
        setWeather();
    }

    private void setWeather()
    {
        if (weather.equals("warm")) wp=1;       // warm
        else if (weather.equals("cold")) wp=2;  // cold
        else if (weather.equals("cloud")) wp=3;  // cloud
        else if (weather.equals("normal")) wp=4;  // normal
    }
    private void setPitch()
    {
        if (pitch.equals("dusty")) pp=1;        // dusty
        else if (pitch.equals("green")) pp=2;   // green/seam
        else if (pitch.equals("seam")) pp=2;   // green/seam
        else if (pitch.equals("normal")) pp=3;   // normal
    }

     private String getConditions(){

        if (wp==1 && pp==1) conditions="c1"; // warm-dusty
        else if (wp==1 && pp==2) conditions="c2"; // warm-Green/Seam
        else if (wp==1 && pp==3) conditions="c3"; // warm-Normal

         else if (wp==2 && pp==1) conditions="c4"; // cold-dusty
         else if (wp==2 && pp==2) conditions="c5"; // cold-Green/Seam
         else if (wp==2 && pp==3) conditions="c6"; // cold-Normal

        else if (wp==3 && pp==1) conditions="c7"; // cloud-dusty
        else if (wp==3 && pp==2) conditions="c8"; // cloud-Green/Seam
        else if (wp==3 && pp==3) conditions="c9"; // cloud-Normal

        else if (wp==4 && pp==1) conditions="c10"; // normal-dusty
        else if (wp==4 && pp==2) conditions="c11"; // normal-Green/Seam
        else if (wp==4 && pp==3) conditions="c12"; // normal-Normal

         return conditions;

     }

    private int getBat(){
        if (getConditions().equals("c1")) bat=6;
        else if (getConditions().equals("c2")) bat=7;
        else if (getConditions().equals("c3")) bat=6;

        else if (getConditions().equals("c4")) bat=6;
        else if (getConditions().equals("c5")) bat=7;
        else if (getConditions().equals("c6")) bat=6;

        else if (getConditions().equals("c7")) bat=7;
        else if (getConditions().equals("c8")) bat=7;
        else if (getConditions().equals("c9")) bat=6;

        else if (getConditions().equals("c10")) bat=6;
        else if (getConditions().equals("c11")) bat=7;
        else  bat=6;

        return  bat; // 6,7
    }


    private int getFast(){
        if (getConditions().equals("c1")) fast=2;
        else if (getConditions().equals("c2")) fast=3;
        else if (getConditions().equals("c3")) fast=3;

        else if (getConditions().equals("c4")) fast=2;
        else if (getConditions().equals("c5")) fast=4;
        else if (getConditions().equals("c6")) fast=3;

        else if (getConditions().equals("c7")) fast=3;
        else if (getConditions().equals("c8")) fast=4;
        else if (getConditions().equals("c9")) fast=3;

        else if (getConditions().equals("c10")) fast=2;
        else if (getConditions().equals("c11")) fast=3;
        else  fast=3;

        return fast; //2,3,4
    }
     private int getSpin(){
        if (getConditions().equals("c1")) spin=4;
        else if (getConditions().equals("c2")) spin=2;
        else if (getConditions().equals("c3")) spin=3;

        else if (getConditions().equals("c4")) spin=2;
        else if (getConditions().equals("c5")) spin=1;
        else if (getConditions().equals("c6")) spin=1;

        else if (getConditions().equals("c7")) spin=2;
        else if (getConditions().equals("c8")) spin=0;
        else if (getConditions().equals("c9")) spin=1;

        else if (getConditions().equals("c10")) spin=3;
        else if (getConditions().equals("c11")) spin=1;
        else  spin=1;

        return spin;//0,1,2,3,4
     }



     public boolean calculateXI(){

        int tBWL;
        nBAT=getBat();
        nBWL=getFast();
        nSPN=getSpin();
        tBWL =getFast()+getSpin();

        if (nBAT>5){
            nALL=nBAT-5;
            nBAT=nBAT-nALL;

        }
        if((nBAT+nALL+tBWL)>11)
        {
            int temp= (nBAT+nALL+tBWL)-11;
            nBWL=nBWL-temp;

        }
         if((nBAT+nALL+tBWL)<11)
         {
             int temp= 11-(nBAT+nALL+tBWL);
             nALL=nALL+temp;

         }


         return true;
//        return nBAT+nBWL+nSPN+nALL;
     }


    public int getnBAT() {
        return nBAT;
    }

    public int getnALL() {
        return nALL;
    }

    public int getnBWL() {
        return nBWL;
    }

    public int getnSPN() {
        return nSPN;
    }
}
