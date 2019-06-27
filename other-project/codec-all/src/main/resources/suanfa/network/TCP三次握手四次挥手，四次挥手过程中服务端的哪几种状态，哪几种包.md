1. 三次握手涉及到包和状态
Client                  Server
                        
 SYN-SEND               LISTEN
 
 ESTABLISHED            ESTABLISHED
 
 
 --------
 
  FIN-WAIT-1            CLOSE_WAIT
  FIN-WAIT-2            LAST-ACk
  CLOSE-WAIT            CLOSED