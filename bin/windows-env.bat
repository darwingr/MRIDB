:: To read the values from a file line by line
:: for / "tokens=*" %a in (c:\path\to\my-values.list) do echo.  Version%~nxa.txt

:: Finds all the lines in the .env file without the # char.
for /f %%x (find /v "#" .env) do (
	set %%x
)
