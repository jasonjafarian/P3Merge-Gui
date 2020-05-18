// 
// Decompiled by Procyon v0.5.30
// 

package p3;

import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;

public class ValidDatasources implements Serializable
{
    public static Map<String, String> datasources;
    
    public ValidDatasources() {
        ValidDatasources.datasources.put("ARMY_CHESS", "ARMY_CHESS");
        ValidDatasources.datasources.put("NITCP", "NITCP");
        ValidDatasources.datasources.put("ADVANTAGE", "ADVANTAGE");
        ValidDatasources.datasources.put("OS2", "OS2");
        ValidDatasources.datasources.put("ITS_RA", "ITS_RA");
        ValidDatasources.datasources.put("FSSI OS3", "FSSI OS3");
        ValidDatasources.datasources.put("FSSI MRO", "FSSI MRO");
        ValidDatasources.datasources.put("GovernmentWide Acquisition Contracts", "GovernmentWide Acquisition Contracts");
        ValidDatasources.datasources.put("FSSI JanSan", "FSSI JanSan");
        ValidDatasources.datasources.put("FSSI Wireless", "FSSI Wireless");
        ValidDatasources.datasources.put("Print Management", "Print Management");
        ValidDatasources.datasources.put("OASIS", "OASIS");
        ValidDatasources.datasources.put("TRANSCOM", "TRANSCOM");
        ValidDatasources.datasources.put("FSSI_DDS3", "FSSI_DDS3");
        ValidDatasources.datasources.put("DOD ESI", "DOD ESI");
        ValidDatasources.datasources.put("GSS", "GSS");
        ValidDatasources.datasources.put("Commodities Enterprise Contract", "Commodities Enterprise Contract");
        ValidDatasources.datasources.put("army_chess", "army_chess");
        ValidDatasources.datasources.put("nitcp", "nitcp");
        ValidDatasources.datasources.put("advantage", "advantage");
        ValidDatasources.datasources.put("os2", "os2");
        ValidDatasources.datasources.put("reverse_auction", "reverse_auction");
        ValidDatasources.datasources.put("fssi_os3", "fssi_os3");
        ValidDatasources.datasources.put("fssi_mro", "fssi_mro");
        ValidDatasources.datasources.put("gov_acq_contracts", "gov_acq_contracts");
        ValidDatasources.datasources.put("fssi_jansan", "fssi_jansan");
        ValidDatasources.datasources.put("fssi_wireless", "fssi_wireless");
        ValidDatasources.datasources.put("print_management", "print_management");
        ValidDatasources.datasources.put("oasis", "oasis");
        ValidDatasources.datasources.put("transcom", "transcom");
        ValidDatasources.datasources.put("fssi_dds3", "fssi_dds3");
        ValidDatasources.datasources.put("dod_esi", "dod_esi");
        ValidDatasources.datasources.put("gss", "gss");
        ValidDatasources.datasources.put("va", "va");
    }
    
    public String getDatasource(final String datasource) {
        if (datasource == null) {
            return null;
        }
        return ValidDatasources.datasources.get(datasource);
    }
    
    static {
        ValidDatasources.datasources = new HashMap<String, String>();
    }
}
