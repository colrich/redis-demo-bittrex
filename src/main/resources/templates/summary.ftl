<!DOCTYPE html>

<html lang="en">


<body bgcolor="#0C642B" text="white">
    <center>
        <h2>Market Summary for ${MarketName}</h2>
        <h4> 24 hour high: ${High}</h4>
        <h4> 24 hour low: ${Low}</h4>
        <h4> Volume: ${Volume}</h4>
        <br /> <br />
        <h4> Bid: ${Bid}, Ask: ${Ask}, Last: ${Last}</h4>
        <h4> Open buy orders: ${OpenBuyOrders}, Open sell orders: ${OpenSellOrders}</h4>
        <br /> <br />
        Data retrieved: ${time?date} / ${time?time}
    </center>
</body>

</html>
