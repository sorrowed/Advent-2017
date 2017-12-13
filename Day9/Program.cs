using System;
using System.Collections.Generic;
using System.IO;

namespace Day9
{
    class Program
    {
        static void Main(string[] args)
        {
            var input = File.ReadAllText("input.txt");

            var p = new Processor();
            p.Run(input);

            Console.Out.WriteLine("Day 9 part 1 scores " + p.Score.ToString());
            Console.Out.WriteLine("Day 9 part 2 garbage " + p.Garbage.ToString());
        }
    }

    class Processor
    {
        private Stack<int> groupsStart = new Stack<int>();
        public int Score { get; private set; }
        public int Garbage { get; private set; }

        public void Run(string input)
        {
            bool inGarbage = false;
            int ix = 0;

            while (ix < input.Length)
            {

                if (inGarbage)
                {
                    if (input[ix] != '>' && input[ix] != '!')
                        ++Garbage;
                }

                switch (input[ix])
                {
                    case '<': // garbage
                        inGarbage = true;
                        break;
                    case '>':
                        inGarbage = false;
                        break;
                    case '!':
                        if (inGarbage)
                            ++ix;
                        break;
                    case '{': // Start group
                        if (!inGarbage)
                            groupsStart.Push(ix);
                        break;
                    case '}': // End group
                        if (!inGarbage)
                        {
                            if (groupsStart.Count > 0)
                            {
                                Score += groupsStart.Count;
                                groupsStart.Pop();
                            }
                        }
                        break;
                    default:
                        break;
                }

                ++ix;
            }
        }
    }
}
