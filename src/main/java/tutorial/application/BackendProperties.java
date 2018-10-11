package tutorial.application;

public interface BackendProperties {
	String PERSISTENCE_UNIT = "tutorial";
	String PROPERTIES_PREFIX = "spring.datasource";
	String REPO_PACKAGE = "tutorial.backend.repositories";
	String ENTITY_PACKAGE = "tutorial.backend.entities";
}