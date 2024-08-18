/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

import java.util.Arrays;
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
    int count= skds.getRegisteredServersCount();
    
    public List<Integer> checkHost(String ipaddress, int numberThreads){

        int numberLists = skds.getRegisteredServersCount();
        int parts = numberLists / numberThreads;
        int residue = numberLists % numberThreads;
        int ocurrences = 0;
        int j = 0;
        int[] partesRango= new int[numberThreads+1];
        for(int i = 0; i <= numberThreads; i++){
            partesRango[i]=i*parts;   
        }
        partesRango[numberThreads] += residue;
        System.out.println(Arrays.toString(partesRango));


        //System.out.println(residue);

        for(int i=1; i<numberThreads+1; i++){
            System.out.println((i-1) * parts + "-" + (i*parts));
            //HostBlackListsValidatorThreads thread = new HostBlackListsValidatorThreads(ipaddress, (i-1) * parts, i*parts);
            //thread.start();
        }
        return new LinkedList<>();
    }



    // private static final int BLACK_LIST_ALARM_COUNT=5;
    
    // /**
    //  * Check the given host's IP address in all the available black lists,
    //  * and report it as NOT Trustworthy when such IP was reported in at least
    //  * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case.
    //  * The search is not exhaustive: When the number of occurrences is equal to
    //  * BLACK_LIST_ALARM_COUNT, the search is finished, the host reported as
    //  * NOT Trustworthy, and the list of the five blacklists returned.
    //  * @param ipaddress suspicious host's IP address.
    //  * @return  Blacklists numbers where the given host's IP address was found.
    //  */
    // public List<Integer> checkHost(String ipaddress, int numberThreads){
        
    //     LinkedList<Integer> blackListOcurrences=new LinkedList<>();
        
    //     int ocurrencesCount=0;
        
    //     HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
        
    //     int checkedListsCount=0;
        
    //     for (int i=0;i<skds.getRegisteredServersCount() && ocurrencesCount<BLACK_LIST_ALARM_COUNT;i++){
    //         checkedListsCount++;
            
    //         if (skds.isInBlackListServer(i, ipaddress)){
                
    //             blackListOcurrences.add(i);
                
    //             ocurrencesCount++;
    //         }
    //     }
        
    //     if (ocurrencesCount>=BLACK_LIST_ALARM_COUNT){
    //         skds.reportAsNotTrustworthy(ipaddress);
    //     }
    //     else{
    //         skds.reportAsTrustworthy(ipaddress);
    //     }                
        
    //     LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{checkedListsCount, skds.getRegisteredServersCount()});
        
    //     return blackListOcurrences;
    // }
    
    
    // private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());
    
    
    
}
