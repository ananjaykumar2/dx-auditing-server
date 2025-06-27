package org.cdpg.dx.auditingserver.common;

import static org.cdpg.dx.auditingserver.activity.util.ActivityConstants.*;
import static org.cdpg.dx.auditingserver.activity.util.ActivityConstants.ORGANIZATION_ID;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.cdpg.dx.auth.authorization.model.DxRole;
import org.cdpg.dx.common.model.DxUser;

public final class ControllerUtil {

  public static Map<String, Object> getAdditionalFilters(DxUser user) {

    List<String> roles = user.roles();

    String organizationId = user.organisationId();
    Map<String, Object> additionalFilters = null;
    if (roles.contains(DxRole.COS_ADMIN.getRole())) {
      additionalFilters = Map.of(MYACTIVITY_ENABLED, true);

    } else {
      additionalFilters = Map.of(MYACTIVITY_ENABLED, true, ORGANIZATION_ID, organizationId);
    }
    return additionalFilters;
  }

  public static Map<String, String> getAllowedFilterMapForAdmin(DxUser user) {
    List<String> roles = user.roles();

    Map<String, String> allowedFilter = new HashMap<>(ALLOWED_FILTER_MAP_FOR_ADMIN);

    if (roles.contains(DxRole.COS_ADMIN.getRole())) {
      allowedFilter.put("organizationId", ORGANIZATION_ID);
    }
    return allowedFilter;
  }
}
