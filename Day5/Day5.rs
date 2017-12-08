use std::fs::File;
use std::io::prelude::*;

fn read() -> Vec<i64> {
    let mut f = File::open("input.txt").expect("Feck!");

    let mut contents = String::new();

    f.read_to_string(&mut contents).expect("Whut?!");

    return contents
        .split_whitespace()
        .map(|x| x.parse::<i64>().unwrap())
        .collect::<Vec<i64>>();
}

fn part1() -> i32 {
    let mut vec = read();

    let mut ix = 0;
    let mut steps = 0;
    while ix < vec.len() {
        let elem = vec[ix];
        vec[ix] += 1;
        ix = (ix as i64 + elem) as usize;
        steps += 1;
    }
    return steps;
}

fn part2() -> i32 {
    let mut vec = read();

    let mut ix = 0;
    let mut steps = 0;
    while ix < vec.len() {
        let elem = vec[ix];
        if vec[ix] >= 3 {
            vec[ix] -= 1;
        } else {
            vec[ix] += 1;
        }
        ix = (ix as i64 + elem) as usize;
        steps += 1;
    }
    return steps;
}

fn main() {
    println!("Part1 : Escaped after {0} steps", part1());
    println!("Part2 : Escaped after {0} steps", part2());
}
