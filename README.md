# SemestralnaPraca
Popis hry „Tanky“

Plánom mojej práce je vytvoriť hru pod názvom „Tanky“, v ktorej hráč bojuje s vlastným tankom
zvolenej frakcie (modrý team/červený team) proti protivníkom. Cieľom hry je zasiahnúť nepriateľský
tank a ovládnuť mapu. Po mape sú rozmiestnené rôzne typy munície, ktoré keď hráč prejde svojim
tankom, má možnosť ju následne použiť proti protivníkovi. Každý zásah nepriateľa alebo zásah od
nepriateľa sa boduje. Body za zásah sa pričíta na stranu frakcie do rebríčka, ktorý je zobrazený
v strede, v hornej časti obrazovky. Akonáhle bude daný počet bodov dosiahnutý, vyhráva frakcia,
ktorá body dosiahla ako prvá. Po doposiaľ dosiahnutých výhrach/prehrách sa tvorí štatistika hráča,
ktorú si hráč môže pozrieť v menu hry „pozriSvojeStatistiky“.

Ovládanie hry
Tank sa pohybuje pomocou znakov klávesnice. Klávesy sú nasledovné:
W – pohyb dopredu,
A – pohyb doľava,
S – pohyb dolu,
D – pohyb doprava,
Medzerník – streľba projektilu tam kam hráč ukazuje myšou,
+ - zmeň na ďalší typ munície,
- - zmeň na predošlý typ munície.

V hre je spomenutý mód pre viac hráčov. Pokiaľ sa mi podarí vytvoriť režim pre jedného hráča,
následne budem implementovať mód pre viac hráčov.
