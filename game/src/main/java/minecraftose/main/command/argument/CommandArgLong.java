package minecraftose.main.command.argument;

import minecraftose.main.command.source.CommandSource;

public class CommandArgLong extends CommandArg{
    
    // Результат парсинга
    private long number;
    
    @Override
    public int parse(String remainingChars, CommandSource source){
        // Разделяем оставшуюся часть команды на части
        final String[] args = remainingChars.split(" ");
        
        // Если количество частей меньше 1 (число), завершить парсинг
        if(args.length < 1)
            return 0;
        
        // устанавливаем число
        number = Long.parseLong(args[0]);
        return args[0].length();
    }
    
    public long getLong(){
        return number;
    }
    
}
