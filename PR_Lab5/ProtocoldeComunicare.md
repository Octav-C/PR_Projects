#PROTOCOLUL DE COMUNICARE


**Formatul mesajelor** - *[/command parameter]*

**Comenzile suportate de server**

	1.[/hello name] - Show the Name

	2.[/help] - Show available commands

	3.[/horoscope sign] - Horoscope for current date

	4.[/random Number1 Number2] - Random a number between Number1 and Number2

	5.[/time] - Show Current Time

**Exemplu de raspuns**

	1. send: /hello Name

		1.1. response: Name

	2. send: /help

		2.1. response: [/hello name] - Show the Name

			   [/help] - Show available commands

	                   [/horoscope sign] - Horoscope for current date

			   [/random Number1 Number2] - Random a number between Number1 and Number2

			   [/time] - Show Current Time

	3. send: /horoscope leu

		3.1. response: Show horoscope for current date and indicate sign

	4. send: /horoscope leo

		4.1.response: Incorrect Format [/horoscope sign]

	5. send: /random 5 20

		5.1. response: An random number between 5 and 20

	6. send: /random 20 5

		6.1. response: Incorrect Format [/random Number1 Number2]

	7. send: /time

		7.1. response: Show current time

	8. send: /time param

		8.1. response:Incorrect Format [/time]
