package bit.minisys.minicc.icgen;

import bit.minisys.minicc.parser.ast.ASTNode;
import bit.minisys.minicc.parser.ast.ASTVisitor;

public class DescriptionLabel extends ASTNode {

    public String name;
    public Integer destination;

    public DescriptionLabel() {
        super("descriptionLabel");
        this.name = "";
        this.destination = 0;
    }

    public DescriptionLabel(String name) {
        super("descriptionLabel");
        this.name = name;
        this.destination = 0;
    }

    public DescriptionLabel(String name, Integer dest) {
        super("descriptionLabel");
        this.name = name;
        this.destination = dest;
    }

    @Override
    public void accept(ASTVisitor visitor) throws Exception {

    }
}
