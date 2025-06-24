# Trading Card Inventory System (TCIS)

## Program Overview

This repository contains the specifications for the Trading Card Inventory System (TCIS), a project designed to help card collectors manage, organize, and trade cards from their collections. [cite_start]The system aims to simplify the often overwhelming tasks of tracking ever-growing collections and ensuring fair trades based on fluctuating card values.

## Functionalities

The TCIS will include the following core functionalities:

### 1. Add Card

This feature allows a collector to add a card to their collection. Users will input the following details for each card:

* **Card name:** The name of the card, typically composed of letters but may contain special characters. Card names are unique; if a card is added with a name matching an existing card, the user should be notified and given the option to increase the count of the card instead.
* **Rarity:** Cards can be common, uncommon, rare, or legendary. Rarity should not be confused with Variant.
* **Variant:** Rare and legendary cards can be normal, extended-art, full-art, or alt-art. If a card is not rare or legendary, its variant is always normal. Card variants influence the price of the card:
    * Normal: No increase in value.
    * Extended-art: Value is increased by 50%.
    * Full-art: Value is increased by 100%.
    * Alt-art: Value is increased by 200%.
* **Value:** Corresponds to the dollar value of a card. For cards with variants, the user inputs only the base value, not the increased value due to the card's variant.

All details for cards with the same name, including their variant, must be identical. For example, if an "alt-art" "Pikachu" is added to the collection, no other cards with the name "Pikachu" may be added unless it is a second copy of the same "alt-art" "Pikachu".

### 2. Increase/Decrease Card Count

Collectors usually keep more than one copy of a card. When a card is added to the collection, its count is automatically set to 1. The collector may increase or decrease this count. If the count of a card becomes 0, the card should not be removed from the inventory, but it should not be selectable for adding to decks or binders.

### 3. Display a Card/Display Collection

Collectors should be able to view the details of a card by selecting it via its name or its number in the collection. The rest of its details should then be displayed on screen. The collector may also view their whole collection. Only the names of the cards in their collection and the count of each card must be displayed, sorted alphabetically in ascending order by card name. The collection of a collector has no limit; you can store as many cards as you want, and any number of copies (limited practically by max integer size).

### 4. Manage Binders

Collectors usually put their cards in binders for trading. Your TCIS should allow collectors to:

* **Create a new Binder:** A new binder has a name and can hold up to twenty cards (twenty slots). Multiple cards of the same name can be put in the same binder, with each card occupying its own slot. A binder can only have twenty cards, but can hold any number of copies of a specific card (limited by the size of the binder).
* **Delete a Binder:** A collector must be able to delete a binder from the TCIS. If they do, all cards currently in the binder will be transferred back to the collection of the collector.
* **Add/Remove Card to/from the Binder:** Cards can be added or removed from a binder. If a card is added to a binder, it is removed from the collection of the player. Should a card be removed from a binder, it is returned to the collection of the player.
* **Trade Card:** Cards can be traded with other cards. For MCO1, trades can only be one card for one card. In a trade, the collector selects a card from their binder to give up (the outgoing card) and will receive a card (the incoming card) from outside their binder (and indeed outside their collection) in return. Since the incoming card is not from the collection, it should be added to the collection first. Once added, the values of the incoming and outgoing card are compared. The collector will then be notified if the difference is greater than or equal to a dollar. The collector should be given the option to cancel the trade at this point. If they do, the incoming card is not added to the collection, and the outgoing card returns to the binder. If they do not cancel, or the trade proceeds normally because the values of the cards to be traded have a difference of less than a dollar, then the incoming card takes the slot of the outgoing card in the binder. A collector cannot trade cards directly from the collection; cards for trade must first be put into a binder. As such, the trade functionality is connected to the binder, not the collection.
* **View Binder:** Display as a list all the cards in a selected binder. Similar to displaying the whole collection, the list of card names in the binder must be displayed, in alphabetical order. If two or more cards with the same name are in the binder, the name of the card would be listed multiple times, depending on how many cards of that name are in the binder.

### 5. Manage Decks

Trading cards are often also game pieces. While you are not going to program the game itself, you should allow collectors to manage and keep track of their decks. Your TCIS should therefore allow collectors to:

* **Create a new Deck:** A deck has a name and can hold up to ten cards. No more than one copy of a card can be in a deck (i.e., a card bearing the same name as another card in the deck may not be added to said deck but may be added to a different deck). A deck can only have ten unique cards (i.e., cards with different names).
* **Delete a Deck:** Like deleting binders, if a deck is deleted, the cards in the deck are returned to the collection.
* **Add/Remove Card to/from the Deck:** Cards can be added or removed from a deck. If a card is added to a deck, it is removed from the collection of the player. [cite_start]Should a card be removed from a deck, it is returned to the collection of the player. Note that a second copy of a card should not be allowed to be added to the deck, and the collector should be notified if this is the case.
* **View Deck:** Display as a list all the cards that are in the deck. The collector should be given the option to view a card in the deck by selecting either its name or its number in the deck. Doing so will display all the details of the selected card.

## Other Requirements

1.  **Design and Implementation:** The design and implementation of the solution should conform to the specifications described above and exhibit proper object-based concepts, like encapsulation and information-hiding.
2.  **User Interface:** Interaction with the user (i.e., input and output) should be through a command line interface (CLI). No graphical interface is expected for MCO1.
3.  **Libraries:** To allow for an easier time to validate the program, usage of libraries outside of what is available in the Java API is not allowed.
4.  **Future Extensions:** Please also note that MCO2 will look to extend some mechanisms of MCO1; however, MCO2's specifications will be delayed in order to simulate additional mechanics or modifications requested by a client after development of the base system. Additionally, a graphical user interface should be implemented only for MCO2.
5.  **Object-Based Programming:** You are also required to create and use methods and classes whenever possible. Make sure to use Object-Based (for MCO1) and Object-Oriented (for MCO2) Programming concepts properly. No brute force solution. When in doubt, consult with your instructor.
6.  **External Statements/Methods:** Statements and methods not taught in class can be used in the implementation. However, these are left for the student to learn on his or her own.

## Screenflow and Text-based User Interface

As the application loads, show the user a menu that allows them to select what action they would wish to perform. Initially it would have the following options, but more options can be added as binders and decks get added to the collection:

* Add a Card
* Create a new Binder
* Create a new Deck

Once a card exists in the collection of the user, a new option should now be added to the main menu that would allow the user to increase or decrease card count. Binders and decks can be created even without cards in the collection. Of course, if this is the case, the binders and decks cannot be populated with anything. [cite_start]Upon creation of a deck or binder, change the menu options from "Create a new Binder" to "Manage Binders", and from "Create a new Deck" to "Manage Decks". Selecting these menu options takes the user to the menu options found in the Manage Binders and Manage Decks sections respectively.

[cite_start]When going through any menu item, add a "Go back to Main Menu" option when needed. This should bring the user back to the Main Menu, cancelling whatever they were currently doing (e.g., as the user is inputting details about a card, they decide they don't want to proceed anymore; in this case, the card they were inputting details for won't be added to the collection).

## General Instructions

### Deliverables

The deliverables for both MCOs include:

1.  Signed declaration of original work (declaration of sources and citations may also be placed here). [cite_start]See Appendix A for an example.
2.  Softcopy of the class diagram following UML notations. [cite_start]Kindly ensure that the diagram is easy to read and well structured.
3.  Javadoc-generated documentation for proponent-defined classes with pertinent information.
4.  Zip file containing the source code with proper internal documentation.
    * [The program must be written in Java.
    * Test script following the format indicated in Appendix B.
5.  A video demonstration of your program.
    * While groups have the freedom to conduct their demonstration, a demo script will be provided closer to the due date to help with showing the expected functionalities.
    * The demonstration should also quickly explain key aspects of the program's design found in the group's class diagram.
    * Please keep the demo as concise as possible and refrain from adding unnecessary information.

### Submission

All deliverables for the MCO are to be submitted via Canvas. Submissions made in other venues will not be accepted. Please also make sure to take note of the deadlines specified on Canvas. No late submissions will be accepted.

### Grading

For grading of the MCO, please refer to the MCO rubrics indicated in the syllabus or the appendix.

### Collaboration and Academic Honesty

This project is meant to be worked on as a pair (i.e., max of 2 members in a group). In exceptional cases, a student may be allowed by their instructor to work on the project alone; however, permission should be sought as collaboration is a key component of the learning experience. Under no circumstance will a group be allowed to work on the MCO with more than 2 members. A student cannot discuss or ask about design or implementation with other persons, with the exception of the teacher and their groupmate. Copying other people's work and/or working in collaboration with other teams are not allowed and are punishable by a grade of 0.0 for the entire CCPROG3 course and a case may be filed with the Discipline Office. In short, do not risk it; the consequences are not worth the reward. Comply with the policies on collaboration and AI usage as discussed in the course syllabus.

### Documentation and Coding Standards

Do not forget to include internal documentation (comments) in your code. At the very least, there should be an introductory comment and a comment before every class and every method. This will be used later to generate the required External Documentation for your Machine Project. You may use an IDE or the appropriate command-based instructions to create the documentation, but it must be PROPERLY constructed. Please note that we're not expecting you to add comments for each and every line of code. A well-documented program also implies that coding standards are adhered to in such a way that they aid in the documentation of the code. Comments should be considered for more complex logic.

### Bonus Points

No bonus points will be awarded for MCO1. Bonus points will only be awarded for MCO2. To encourage the usage of version control, please note that a small portion of the bonus points for MCO2 will be the usage of version control. Please consider using version control as early as MCO1 to help with collaborating within the group.

### Resources and Citations

All sources should have proper citations. Citations should be written using the APA format. Examples of APA-formatted citations can be seen in the References section of the syllabus. You're encouraged to use the declaration of original work document as the document to place the citations.

### Demonstration Delivery

The mode of delivery of the demo for MCO1 is left to the discretion of your instructor. All members are expected to be present in the video demonstration and should have relatively equal parts in terms of the discussion. Any student who is not present during the demo will receive a zero for the phase. During the MP demo, it is expected that the program can be compiled successfully and will run. If the program does not run, the grade for that phase is 0. However, a running program with complete features may not necessarily get full credit, as implementation (i.e., code) will still be checked.
