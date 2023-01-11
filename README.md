# ShopOnline
Онлайн магазин

Структурируем программу по пакетам согласно процессу деятельности магазина:
    Goods - товар,
    Selection - выбор товара,
    Order - заказ,
    Tracking - доставка товара,
    Menu - все, что связано с организацией взаимодействия с пользователем.

    Строим код по следующим принципам:
    Magic - все константы необходимые либо именуем, либо определяем соответствующий enumeration.  Все они public,
            а константы еще и final static.
    Single-Responsibility - строго соблюдаем правило: класс - это об одном: о товаре, заказе, сортировке и т.д.
    Open-Closed - для каждого класса основные переменные делаем невидимыми извне, и реализуем все необходимые интерфейсы.
    Liskov substitution - применен один случай наследования: Goods (товар) родительский класс для AboutGoods (дополнительная
            информация о товаре), выдержанный строго по принципу LSP. Считаю такое решение не слишком удачным - лучше было
            бы создать связанный с товаром класс.
    Interface segregation - не всегда выдержано, но выполнено для основных классов: Goods, AboutGoods, Order, Ordering,
            ReadWriteGoods, интерфейсы для классов сортировки и поиска (SelertArray и SortArray).
    Dependency inversion - реализация DIP наиболее последовательно проведена в решении по структуре взаимодействия с
            пользователем (см. ниже).

    Несколько слов о реализации.
        1.  Структура программы определялась двумя обстоятельствами: предметной областью, определившей существенные классы,
            и представлением о взаимодействии с пользователем, определившем структуру кода программы.
        2.  В основе реализации меню лежит класс Menu, содержащим две основные компоненты: обработчиков выбора вариантов
            (execMenu) и ввода значений параметров (stringMenu). Организация же собственно меню (в соответствии с DIP)
            реализована через класс MenuDefinition, который производит вызов необходимых программных компонент.
        3.  Имитация доставки товара (трекинг) выполнена как диалог клиента (служба заказа) и сервера (служба доставки).
            Поэтому для запуска необходимо запустить сервер (модуль Server).
        4.  Для поиска товара реализованы интексы в классе GoodsSelecting. Ключом к для поиска служат наименование товара,
            ключевые слова и производитель. Отдельный индекс сделан для поска товара по ID.
        5.  Справочник товаров хранится в csv-файле, а заказы в json-файле (не спрашивайте почему так решил).
        6.  Хорошее комментирование, как и глубокое тестирование выполнить не удалось - времени не хватило...