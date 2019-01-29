package bupt.pangu.component;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.Map;

@Named
@RequestScoped
public class MacaronComponent {
    private Map<String, Object> attrs;

    public Map<String, Object> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<String, Object> attrs) {
        this.attrs = attrs;
    }
}
