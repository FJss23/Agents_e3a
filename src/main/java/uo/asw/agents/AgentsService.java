package uo.asw.agents;


import uo.asw.agents.util.LoaderMin;
import uo.asw.dbManagement.model.Loader;

/**
 * Servicio de agentes
 * @since 0.0.1
 */
public interface AgentsService {
    LoaderMin getAgentsInfo(String login, String password, String kind);
    Loader changeInfo(Loader updatedData);
}
