package edu.eci.arsw.blacklistvalidator;

import java.util.LinkedList;
import java.util.List;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

public class HostBlackListsValidatorThreads extends Thread {

    private int ocurrences;
    private int checkedListsCount;
    private String ipaddress;
    private int start;
    private int end;
    private LinkedList<Integer> blackListOcurrences;

    public HostBlackListsValidatorThreads(String ipaddress, int start, int end){
        this.ipaddress = ipaddress;
        this.start = start;
        this.end = end;
        this.ocurrences = 0;
        blackListOcurrences=new LinkedList<>();
    }

    public void run(){
        this.checkHost(ipaddress, start, end);
    }

    public void checkHost(String ipaddress, int start, int end){
        HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
        for (int i=start;i<end;i++){
            checkedListsCount += 1;
            if (skds.isInBlackListServer(i, ipaddress)){
                blackListOcurrences.add(i);
                ocurrences++;
            }
        }
    }

    public List<Integer> getBlackListOcurrences(){
        return blackListOcurrences;
    }

    public int getOcurrences(){
        return ocurrences;
    }

    public int getCheckedListsCount(){
        return checkedListsCount;
    }

}
