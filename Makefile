JC=javac
OBLIG_1=thomaswaaler/oblig1
OBLIG_2=thomaswaaler/oblig2
OBLIG_3=thomaswaaler/oblig3/SortingMethods

obligs: $(OBLIG_1)/Flyplassen.class $(OBLIG_2)/SpringerProblemet.class $(OBLIG_3).class

$(OBLIG_1)/Flyplassen.class: $(OBLIG_1)/Flyplassen.java
	$(JC) $(OBLIG_1)/Flyplassen.java

$(OBLIG_2)/SpringerProblemet.class: $(OBLIG_2)/SpringerProblemet.java
	$(JC) $(OBLIG_2)/SpringerProblemet.java

$(OBLIG_3).class: $(OBLIG_3).java
	$(JC) $(OBLIG_3).java
