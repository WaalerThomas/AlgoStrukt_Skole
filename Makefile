JC=javac
OBLIG_1=thomaswaaler/oblig1
OBLIG_2=thomaswaaler/oblig2

obligs: $(OBLIG_1)/Flyplassen.class $(OBLIG_2)/SpringerProblemet.class

$(OBLIG_1)/Flyplassen.class: $(OBLIG_1)/Flyplassen.java
	$(JC) $(OBLIG_1)/Flyplassen.java

$(OBLIG_2)/SpringerProblemet.class: $(OBLIG_2)/SpringerProblemet.java
	$(JC) $(OBLIG_2)/SpringerProblemet.java
