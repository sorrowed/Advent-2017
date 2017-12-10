package main

import (
	"fmt"
	"io/ioutil"
	"strings"
)

type Operation struct {
	dreg  string
	op    string
	parm  int
	sreg  string
	cmp   string
	testv int
}

func inc(o *Operation) int {
	registers[o.dreg] += o.parm
	return registers[o.dreg]
}

func dec(o *Operation) int {
	registers[o.dreg] -= o.parm

	return registers[o.dreg]
}

func lt(o *Operation) bool {
	return registers[o.sreg] < o.testv
}

func gt(o *Operation) bool {
	return registers[o.sreg] > o.testv
}

func leq(o *Operation) bool {
	return registers[o.sreg] <= o.testv
}

func geq(o *Operation) bool {
	return registers[o.sreg] >= o.testv
}

func eq(o *Operation) bool {
	return registers[o.sreg] == o.testv
}

func neq(o *Operation) bool {
	return registers[o.sreg] != o.testv
}

func (o *Operation) Parse(line string) {
	var skip string
	_, err := fmt.Sscanf(line, "%s %s %d %s %s %s %d",
		&o.dreg, &o.op, &o.parm,
		&skip,
		&o.sreg, &o.cmp, &o.testv)

	if err != nil {
		fmt.Println(err)
	}
}

var registers map[string]int

func (o *Operation) Execute() int {

	compararators := map[string]func(*Operation) bool{
		"<":  lt,
		">":  gt,
		"<=": leq,
		">=": geq,
		"==": eq,
		"!=": neq,
	}
	var cmp = compararators[o.cmp]

	operators := map[string]func(*Operation) int{
		"inc": inc,
		"dec": dec,
	}

	var r int
	var oper = operators[o.op]
	if cmp != nil && cmp(o) {
		r = oper(o)
	}
	return r
}

func parse() []Operation {
	d, err := ioutil.ReadFile("input.txt")
	if err != nil {
		fmt.Print(err)
	}
	lines := strings.Split(string(d), "\n")

	var operations = make([]Operation, 0)

	for _, line := range lines {
		op := Operation{}
		op.Parse(line)
		operations = append(operations, op)
	}

	return operations
}

func main() {

	var operations = parse()

	registers = make(map[string]int)

	var maxd, maxe int

	for _, op := range operations {
		var m = op.Execute()
		if m > maxd {
			maxd = m
		}
	}

	for _, r := range registers {
		if r > maxe {
			maxe = r
		}
	}

	fmt.Printf("Part 1&2: The maximum values after and during are %d and %d\n", maxe, maxd)
}
