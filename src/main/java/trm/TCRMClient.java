package trm;

import java.rmi.RemoteException;

/**
 * TCRM Java Web Service demo client
 * 
 * @author Alex Vicente ChacOn JimEnez (alex.chacon@software-colombia.com)
 * @since JDK 1.7.0_04
 * @version 1.0
 */
public class TCRMClient
{
	
	/**
	 * Web Service end point
	 */
	private static final String WEB_SERVICE_URL = "https://www.superfinanciera.gov.co/SuperfinancieraWebServiceTRM/TCRMServicesWebService/TCRMServicesWebService?WSDL";

	
	public Float obtenerTRMActual() throws RemoteException
	{
		TCRMServicesInterfaceProxy proxy = new TCRMServicesInterfaceProxy(WEB_SERVICE_URL);
		TcrmResponse tcrmResponse = proxy.queryTCRM(null);
		
		return tcrmResponse.getValue();
	}
}