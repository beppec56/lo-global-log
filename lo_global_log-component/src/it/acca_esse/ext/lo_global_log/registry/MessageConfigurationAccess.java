package it.acca_esse.ext.lo_global_log.registry;

import com.sun.star.beans.XPropertySet;

import com.sun.star.container.XNameAccess;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XEventListener;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.uno.AnyConverter;
import com.sun.star.uno.Exception;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;

import it.acca_esse.ext.lo_global_log.GlobalConstant;


public class MessageConfigurationAccess extends ConfigurationAccess implements XComponent {

	private Object m_oMessagesRegKey = null;

	public MessageConfigurationAccess(XComponentContext _xContext, XMultiComponentFactory _xMCF) {
		// printlnName("ctor");
		
		super(_xContext );
		try {
			m_oMessagesRegKey = createConfigurationReadOnlyView(GlobalConstant.m_sEXTENSION_CONF_BASE_KEY+"Messages/");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public String getStringFromRegistry( String _stringIdToRetrieve ) throws Exception {
    	if(_stringIdToRetrieve == null)
    		return "";
    	String retVal = new String(_stringIdToRetrieve);
    	if(m_oMessagesRegKey != null) {
    		//get the string at id1
    	    // accessing a single nested value
//FIXME: debug, remove when done 
//Utilities.showInterfaces(m_oMessagesRegKey, m_oMessagesRegKey);
    		XNameAccess xNameAccess =  (XNameAccess) UnoRuntime.queryInterface(XNameAccess.class, m_oMessagesRegKey);
    		if( xNameAccess != null) {
    			if(xNameAccess.hasByName(_stringIdToRetrieve)) {
	    			Object oObj = xNameAccess.getByName( _stringIdToRetrieve );

	    			XPropertySet xPS = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class, oObj);
	    			retVal = AnyConverter.toString( xPS.getPropertyValue( "Text" ) );
    			}
    			else {
//	    			m_logger.info("no element id: "+_stringIdToRetrieve);
    			}
    		}
    		else {
    		//	m_logger.info("XNameAccess","Missing interface");
    		}
    	}
		return retVal; 
    }

	public void addEventListener(XEventListener arg0) {
//		m_logger.info("addEventListener");
	}

	public void dispose() {
		synchronized (this) {
			if(m_oMessagesRegKey != null) {
		        // now clean up
		        ((XComponent) UnoRuntime.queryInterface(XComponent.class, m_oMessagesRegKey)).dispose();
		        m_oMessagesRegKey = null;
			}
		}
	}

	public void removeEventListener(XEventListener arg0) {
//		m_logger.info("removeEventListener");		
	}
}
