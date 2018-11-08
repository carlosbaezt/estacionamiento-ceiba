package trm;

public interface TCRMServicesInterface extends java.rmi.Remote 
{
    public trm.TcrmResponse queryTCRM(java.util.Calendar tcrmQueryAssociatedDate) throws java.rmi.RemoteException;
}
