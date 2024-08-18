/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class HostBlackListsValidator {
    HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
    private static final int BLACK_LIST_ALARM_COUNT=5;

    public List<Integer> checkHost(String ipaddress, int numberThreads){
        LinkedList<Integer> blackListOcurrences=new LinkedList<Integer>();
        int[] partesRango = getRanges(numberThreads);
        ArrayList<HostBlackListsValidatorThreads> threads = new ArrayList<HostBlackListsValidatorThreads>();
        for(int i=0; i<numberThreads; i++){
            HostBlackListsValidatorThreads thread = new HostBlackListsValidatorThreads(ipaddress, partesRango[i], partesRango[i+1]);
            thread.start();
            threads.add(thread);
        }

        int ocurrencesCount = 0;
        int checkedListsCount = 0;
        for(HostBlackListsValidatorThreads i: threads){
            try {
                i.join();
                blackListOcurrences.addAll(i.getBlackListOcurrences());
                ocurrencesCount += i.getOcurrences();
                checkedListsCount += i.getCheckedListsCount();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (ocurrencesCount>=BLACK_LIST_ALARM_COUNT){
            skds.reportAsNotTrustworthy(ipaddress);
        }
        else{
            skds.reportAsTrustworthy(ipaddress);
        }
     
        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{checkedListsCount, skds.getRegisteredServersCount()});
        return blackListOcurrences;
    }

    private int[] getRanges(int numberThreads){
        int numberLists = skds.getRegisteredServersCount();
        int residue = numberLists % numberThreads;
        int parts = numberLists / numberThreads;
        int[] partesRango= new int[numberThreads+1];
        int j = 0;
        for(int i = 0; i <= numberThreads; i++){
            partesRango[i] = j;
            j += parts;
            if(i < residue){
                j += 1;
            }
        }
        return partesRango;
    }

    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());
}
