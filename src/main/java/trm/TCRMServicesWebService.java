package trm;

public interface TCRMServicesWebService {
	public java.lang.String getTCRMServicesWebServicePortAddress();

	public trm.TCRMServicesInterface getTCRMServicesWebServicePort() throws javax.xml.rpc.ServiceException;

	public trm.TCRMServicesInterface getTCRMServicesWebServicePort( java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
